package alararestaurant.service;

import alararestaurant.domain.dtos.xmlImport.ItemXmlDto;
import alararestaurant.domain.dtos.xmlImport.OrderDto;
import alararestaurant.domain.dtos.xmlImport.OrderWrapperDto;
import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Item;
import alararestaurant.domain.entities.Order;
import alararestaurant.domain.entities.OrderItem;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.ItemRepository;
import alararestaurant.repository.OrderItemRepository;
import alararestaurant.repository.OrderRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import alararestaurant.util.XmlParser;
import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {
    private static final String ORDERS_FILE_PATH = "src\\main\\resources\\files\\orders.xml";

    private final OrderRepository orderRepository;
    private final EmployeeRepository employeeRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, EmployeeRepository employeeRepository,
                            ItemRepository itemRepository, OrderItemRepository orderItemRepository, FileUtil fileUtil,
                            XmlParser xmlParser, ModelMapper mapper, ValidationUtil validationUtil) {
        this.orderRepository = orderRepository;
        this.employeeRepository = employeeRepository;
        this.itemRepository = itemRepository;
        this.orderItemRepository = orderItemRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean ordersAreImported() {
        return this.orderRepository.count() > 0;
    }

    @Override
    public String readOrdersXmlFile() throws IOException {
        return this.fileUtil.readFile(ORDERS_FILE_PATH);
    }

    @Override
    public String importOrders() throws JAXBException {
        OrderWrapperDto orderWrapperDto = this.xmlParser.fromXml(OrderWrapperDto.class, ORDERS_FILE_PATH);

        StringBuilder output = new StringBuilder();
        outer:
        for (OrderDto orderDto : orderWrapperDto.getOrders()) {
            Order order = this.mapper.map(orderDto, Order.class);
            if (!this.validationUtil.isValid(order)) {
                output.append("Invalid data format.")
                        .append(System.lineSeparator());

                continue;
            }

            Employee employee = this.employeeRepository.findByName(orderDto.getEmployee());
            if (employee == null) {
                output.append("Invalid data format.")
                        .append(System.lineSeparator());

                continue;
            }

            order.setEmployee(employee);
            Set<OrderItem> orderItems = new HashSet<>();
            for (ItemXmlDto itemDto : orderDto.getItems()) {
                Item item = this.itemRepository.findByName(itemDto.getName());
                if (item == null) {
                    output.append("Invalid data format.")
                            .append(System.lineSeparator());

                    continue outer;
                }

                OrderItem orderItem = new OrderItem();
                orderItem.setItem(item);
                orderItem.setQuantity(itemDto.getQuantity());
                orderItem.setOrder(order);

                orderItems.add(orderItem);
            }

            this.orderRepository.saveAndFlush(order);

            order.setOrderItem(orderItems);
            this.orderItemRepository.saveAll(orderItems);

            output.append(String.format("Order for %s on %s added",
                    order.getCustomer(), order.getDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))))
                    .append(System.lineSeparator());
        }

        return output.toString().trim();
    }

    @Override
    public String exportOrdersFinishedByTheBurgerFlippers() {
        List<Order> orders = this.orderRepository
                .findAllByEmployeePositionNameOrderByEmployeeNameAscIdAsc("Burger Flipper");

        StringBuilder output = new StringBuilder();
        for (Order order : orders) {
            StringBuilder itemOutput = new StringBuilder();
            for (OrderItem orderItem : order.getOrderItem()) {
                Item item = orderItem.getItem();
                itemOutput.append("      Name: ").append(item.getName()).append(System.lineSeparator())
                        .append("      Price: ").append(item.getPrice()).append(System.lineSeparator())
                        .append("      Quantity: ").append(orderItem.getQuantity()).append(System.lineSeparator())
                        .append(System.lineSeparator());
            }

            output.append("Name: ").append(order.getEmployee().getName()).append(System.lineSeparator())
                    .append("Orders:").append(System.lineSeparator())
                    .append("   Customer: ").append(order.getCustomer()).append(System.lineSeparator())
                    .append("   Items:").append(System.lineSeparator())
                    .append(itemOutput.toString());
        }

        return output.toString().trim();
    }
}
