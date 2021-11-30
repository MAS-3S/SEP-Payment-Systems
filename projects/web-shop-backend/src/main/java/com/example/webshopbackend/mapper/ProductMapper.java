package com.example.webshopbackend.mapper;

import com.example.webshopbackend.dto.ProductDto;
import com.example.webshopbackend.model.Product;
import com.example.webshopbackend.model.enums.Currency;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

public class ProductMapper {

    public static ProductDto convertToDto(Product product) {
        ProductDto dto = new ProductDto();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setImage(decodeBase64(product.getImage()));
        dto.setPrice(product.getPrice());
        dto.setAvailableBalance(product.getAvailableBalance());
        if(product.getCurrency().equals(Currency.RSD)) {
            dto.setCurrency("RSD");
        } else if (product.getCurrency().equals(Currency.EUR)) {
            dto.setCurrency("EUR");
        }

        return dto;
    }

    public static Product convertToEntity(ProductDto dto) {
        Product product = new Product();

        return product;
    }

    private static String decodeBase64(String image) {
        File currDir = new File(System.getProperty("user.dir"));
        File assetFolder = new File(currDir, "src/main/java/com/example/webshopbackend/assets/images");
        File imagePath = new File(assetFolder, image);
        String encodedFile = "";
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(imagePath);
            byte[] bytes = new byte[(int)imagePath.length()];
            fileInputStreamReader.read(bytes);
            encodedFile = Base64.getEncoder().encodeToString(bytes);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "data:image/jpg;base64," + encodedFile;
    }
}
