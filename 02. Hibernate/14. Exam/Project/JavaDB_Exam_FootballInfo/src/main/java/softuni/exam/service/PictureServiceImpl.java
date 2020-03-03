package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.common.Constants;
import softuni.exam.domain.dtos.pictureImport.PictureImportDto;
import softuni.exam.domain.dtos.pictureImport.PictureImportWrapperDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.ValidatorUtilImpl;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;

import static softuni.exam.common.Constants.INVALID_MESSAGE;
import static softuni.exam.common.Constants.SUCCESSFUL_MESSAGE;


@Service
public class PictureServiceImpl implements PictureService {
    private static final String PICTURE_IMPORT_PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\files\\xml\\pictures.xml";

    private final PictureRepository pictureRepository;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository,
                              FileUtil fileUtil, XmlParser xmlParser,
                              ModelMapper modelMapper, ValidatorUtil validatorUtil) {
        this.pictureRepository = pictureRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public String importPictures() throws JAXBException {
        StringBuilder output = new StringBuilder();
        PictureImportWrapperDto pictureImportWrapperDto =
                this.xmlParser.fromXml(PictureImportWrapperDto.class, PICTURE_IMPORT_PATH);

        for (PictureImportDto pictureDto : pictureImportWrapperDto.getPictures()) {
            Picture picture = this.modelMapper.map(pictureDto, Picture.class);

            if (!this.validatorUtil.isValid(picture)) {
                output.append(String.format(INVALID_MESSAGE, picture.getClass().getSimpleName().toLowerCase()))
                        .append(System.lineSeparator());
                continue;
            }

            this.pictureRepository.saveAndFlush(picture);
            output.append(String.format(SUCCESSFUL_MESSAGE,
                    picture.getClass().getSimpleName().toLowerCase(), picture.getUrl()))
                    .append(System.lineSeparator());
        }

        return output.toString().trim();
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesXmlFile() throws IOException {
        return this.fileUtil.readFile(PICTURE_IMPORT_PATH);
    }

}
