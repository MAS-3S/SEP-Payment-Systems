package com.example.pspservice.mapper;

import com.example.pspservice.dto.PaymentMethodTypeDTO;
import com.example.pspservice.model.PaymentMethodType;

import java.util.ArrayList;
import java.util.List;

public class PaymentMethodTypeMapper {

    public static PaymentMethodTypeDTO mapEntityToDTO(PaymentMethodType methodType) {
        return new PaymentMethodTypeDTO(methodType.getId(), methodType.getName());
    }

    public static List<PaymentMethodTypeDTO> mapEntitiesToDTOs(List<PaymentMethodType> methodTypeList) {
        List<PaymentMethodTypeDTO> dtos = new ArrayList<>();
        for (PaymentMethodType p : methodTypeList) {
            dtos.add(mapEntityToDTO(p));
        }

        return dtos;
    }

}
