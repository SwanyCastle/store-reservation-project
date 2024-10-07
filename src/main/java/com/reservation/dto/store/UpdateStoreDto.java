package com.reservation.dto.store;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateStoreDto {

    private String storeName;
    private String storeAddress;
    private Integer capacityPerson;

}
