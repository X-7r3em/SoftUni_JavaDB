package app.configuration;

import app.domain.dto.query1.ProductResultDto;
import app.domain.dto.query3.CategoryCountDto;
import app.domain.dto.query4.UserResultDto;
import app.domain.dto.query4.WrapperDto;
import app.domain.entities.Category;
import app.domain.entities.Product;
import app.utilities.ValidatorUtility;
import app.utilities.XmlParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Random;
import java.util.Set;

@Configuration
public class AppConfiguration {

    @Bean
    public Gson initializeGSON() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }

    @Bean
    public ModelMapper initializeModelMapper() {
        ModelMapper mapper = new ModelMapper();
        Converter<Product, String> nameConverter = new AbstractConverter<Product, String>() {
            @Override
            protected String convert(Product product) {
                return product == null ? null : product.getSeller().getFirstName() + " " + product.getSeller().getLastName();
            }
        };

        Converter<BigDecimal, String> bigDecimalStringConverter = new AbstractConverter<BigDecimal, String>() {
            @Override
            protected String convert(BigDecimal bigDecimal) {
                return bigDecimal == null ? null : bigDecimal.toString();
            }
        };

        mapper.addMappings(new PropertyMap<Product, ProductResultDto>() {
            @Override
            protected void configure() {
                map().setName(source.getName());
                using(bigDecimalStringConverter).map(source.getPrice()).setPrice(null);
                using(nameConverter).map(source).setSeller(null);
            }
        });

        Converter<Set<Product>, Integer> setSize = new AbstractConverter<Set<Product>, Integer>() {
            @Override
            protected Integer convert(Set<Product> products) {
                return products == null ? null : products.size();
            }
        };

        Converter<Set<Product>, Double> setAverage = new AbstractConverter<Set<Product>, Double>() {
            @Override
            protected Double convert(Set<Product> products) {
                if (products == null) {
                    return null;
                }

                return products.stream().mapToDouble(p -> p.getPrice().doubleValue())
                        .average().orElse(0);
            }
        };

        Converter<Set<Product>, BigDecimal> setRevenue = new AbstractConverter<Set<Product>, BigDecimal>() {
            @Override
            protected BigDecimal convert(Set<Product> products) {
                if (products == null) {
                    return null;
                }

                return products.stream().map(Product::getPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
            }
        };

        mapper.addMappings(new PropertyMap<Category, CategoryCountDto>() {
            @Override
            protected void configure() {
                map().setCategory(source.getName());
                using(setSize).map(source.getProducts()).setProductCount(null);
                using(setAverage).map(source.getProducts()).setAveragePrice(null);
                using(setRevenue).map(source.getProducts()).setTotalRevenue(null);
            }
        });

        return mapper;
    }

    @Bean
    public ValidatorUtility initializeValidatorUtility() {
        return new ValidatorUtility();
    }

    @Bean
    public Random initializeRandom() {
        return new Random();
    }

    @Bean
    public BufferedReader initializeBufferedReader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    @Bean
    public XmlParser initializeXmlParser(){
        return new XmlParser();
    }
}
