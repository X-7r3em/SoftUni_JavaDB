package softuni.exam.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import softuni.exam.domain.dtos.playerImport.PlayerImportDto;
import softuni.exam.domain.dtos.playerImport.TeamPlayerImportDto;
import softuni.exam.domain.entities.Player;
import softuni.exam.domain.entities.Position;
import softuni.exam.domain.entities.Team;
import softuni.exam.util.*;

import javax.validation.Validation;
import javax.validation.Validator;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public FileUtil fileUtil() {
        return new FileUtilImpl();
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    }

    @Bean
    public XmlParser xmlParser() {
        return new XmlParserImpl();
    }

    @Bean
    public Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Bean
    public ValidatorUtil validationUtil() {
        return new ValidatorUtilImpl(validator());
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<String, Position> stringPositionConverter = new AbstractConverter<String, Position>() {
            @Override
            protected Position convert(String s) {
                for (Position value : Position.values()) {
                    if (value.name().equals(s)){
                        return Position.valueOf(s);
                    }
                }

                return null;
            };
        };

        modelMapper.addMappings(new PropertyMap<PlayerImportDto, Player>() {
            @Override
            protected void configure() {
                using(stringPositionConverter).map(source.getPosition()).setPosition(null);
            }
        });

        return modelMapper;
    }
}
