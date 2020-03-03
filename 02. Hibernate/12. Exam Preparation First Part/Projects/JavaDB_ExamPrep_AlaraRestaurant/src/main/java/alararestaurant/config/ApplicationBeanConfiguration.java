package alararestaurant.config;

import alararestaurant.domain.dtos.xmlImport.OrderDto;
import alararestaurant.domain.entities.Order;
import alararestaurant.domain.entities.OrderType;
import alararestaurant.util.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public FileUtil fileUtil() {
        return new FileUtilImpl();
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }

    @Bean
    public ValidationUtil validationUtil() {
        return new ValidationUtilImpl();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        Converter<String, LocalDateTime> stringLocalDateTimeConverter = new AbstractConverter<String, LocalDateTime>() {
            @Override
            protected LocalDateTime convert(String s) {
                return LocalDateTime.parse(s, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            }
        };

        Converter<String, OrderType> stringOrderTypeConverter = new AbstractConverter<String, OrderType>() {
            @Override
            protected OrderType convert(String s) {
                OrderType[] orderTypes = OrderType.values();
                for (OrderType orderType : orderTypes) {
                    if (orderType.name().equals(s)){
                        return OrderType.valueOf(s);
                    }
                }

                return OrderType.ForHere;
            }
        };

        mapper.addMappings(new PropertyMap<OrderDto, Order>() {
            @Override
            protected void configure() {
                using(stringLocalDateTimeConverter).map(source.getDateTime()).setDateTime(null);
                using(stringOrderTypeConverter).map(source.getType()).setType(null);
            }
        });

        return mapper;
    }

    @Bean
    public XmlParser xmlParser() {
        return new XmlParserImpl();
    }
}
