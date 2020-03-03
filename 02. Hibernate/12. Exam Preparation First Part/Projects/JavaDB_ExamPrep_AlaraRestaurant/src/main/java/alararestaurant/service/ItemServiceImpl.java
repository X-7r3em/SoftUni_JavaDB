package alararestaurant.service;

import alararestaurant.domain.dtos.JSONimport.ItemDto;
import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Item;
import alararestaurant.repository.CategoryRepository;
import alararestaurant.repository.ItemRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ItemServiceImpl implements ItemService {
    private static final String ITEMS_FILE_PATH = "src\\main\\resources\\files\\items.json";

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, CategoryRepository categoryRepository,
                           FileUtil fileUtil, Gson gson, ModelMapper mapper, ValidationUtil validationUtil) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean itemsAreImported() {
        return this.itemRepository.count() > 0;
    }

    @Override
    public String readItemsJsonFile() throws IOException {
        return this.fileUtil.readFile(ITEMS_FILE_PATH);
    }

    @Override
    public String importItems(String items) {
        ItemDto[] itemDtos = this.gson.fromJson(items, ItemDto[].class);

        StringBuilder output = new StringBuilder();
        for (ItemDto itemDto : itemDtos) {
            if (this.itemRepository.findByName(itemDto.getName()) != null) {
                output.append("Invalid data format.")
                        .append(System.lineSeparator());

                continue;
            }

            Item item = this.mapper.map(itemDto, Item.class);
            if (!this.validationUtil.isValid(item)) {
                output.append("Invalid data format.")
                        .append(System.lineSeparator());

                continue;
            }

            Category category = this.categoryRepository.findByName(itemDto.getCategory());

            if (category == null) {
                category = new Category();
                category.setName(itemDto.getCategory());
                if (!this.validationUtil.isValid(category)) {
                    output.append("Invalid data format.")
                            .append(System.lineSeparator());

                    continue;
                }

                this.categoryRepository.saveAndFlush(category);
            }

            item.setCategory(category);

            this.itemRepository.saveAndFlush(item);
            output.append(String.format("Record %s successfully imported.", item.getName()))
                    .append(System.lineSeparator());
        }

        return output.toString().trim();
    }
}
