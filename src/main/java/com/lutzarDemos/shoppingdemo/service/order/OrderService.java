package com.lutzarDemos.shoppingdemo.service.order;

import com.lutzarDemos.shoppingdemo.dto.OrderDto;
import com.lutzarDemos.shoppingdemo.enums.OrderStatus;
import com.lutzarDemos.shoppingdemo.exceptions.ResourceNotFoundException;
import com.lutzarDemos.shoppingdemo.model.Cart;
import com.lutzarDemos.shoppingdemo.model.Order;
import com.lutzarDemos.shoppingdemo.model.OrderItem;
import com.lutzarDemos.shoppingdemo.model.Product;
import com.lutzarDemos.shoppingdemo.repository.OrderRepository;
import com.lutzarDemos.shoppingdemo.repository.ProductRepository;
import com.lutzarDemos.shoppingdemo.service.cart.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

/**
 * Contains override methods relating to the ORDER entity
 *      for business logic and application functionality
 *
 * @author      Lutzar
 * @version     1.3, 2024/09/10
 */
@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;

    /**
     * Finds CART, creates new ORDER from CART, creates list of ORDER ITEMS
     *      from the ORDER & CART, updates the ORDER with the ORDER ITEMS,
     *      the TOTAL AMOUNT of the items, saves it to the ORDER REPOSITORY,
     *      clears the CART
     *
     * @param userId    The ID of the USER making the ORDER
     * @return          the new saved ORDER
     */
    @Transactional
    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItems(order, cart);
        order.setOrderItems(new HashSet<>(orderItemList));
        order.setOrderTotalAmount(calculateTotalAmount(orderItemList));
        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(cart.getId());
        return savedOrder;
    }

    /**
     * Creates a new ORDER from a USER's CART
     *
     * @param cart      The CART where a USER has been added PRODUCTs
     */
    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    /**
     * Iterates through a list of ORDER ITEMs and subtracts their inventory
     *      from the PRODUCT REPOSITORY quantity of the PRODUCTs in the ORDER
     *
     * @param order     The ORDER containing the ORDER ITEMs
     * @param cart      The CART containing what PRODUCTs need to be ordered
     * @return          A new ORDER ITEM is created for each PRODUCT in the CART
     */
    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return cart.getItems()
                .stream()
                .map(cartItem -> {
                    Product product = cartItem.getProduct();
                    product.setInventory(product.getInventory() - cartItem.getQuantity());
                    productRepository.save(product);
                    return new OrderItem(
                            order,
                            product,
                            cartItem.getQuantity(),
                            cartItem.getUnitPrice()
                    );
                }).toList();
    }

    /**
     * Iterates through all ORDER ITEMS in an ORDER to calculate the TOTAL PRICE
     *
     * @param orderItemList     The list of ORDER ITEMS to be calculated
     */
    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList) {
        return orderItemList.stream()
                .map(item -> item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Finds the ORDER by the ORDER_ID
     *
     * @param orderId   The ID of the ORDER to find in the ORDER REPOSITORY
     * @return          The ORDER requested
     */
    @Override
    public OrderDto getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this :: convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Order Not Found!"));
    }

    /**
     * Find's USER's ORDERs by the ID
     *      converts them to ORDER DTOs
     *
     * @param userId    the USER
     * @return          List of ORDER DTOs that exist
     */
    @Override
    public List<OrderDto> getUserOrders(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    /**
     * Converts an ORDER to an ORDER DTO
     *
     * @param order     ORDER being converted to an ORDER DTO
     * @return          Converted ORDER DTO
     */
    @Override
    public OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }
}
