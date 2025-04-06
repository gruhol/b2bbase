package pl.thinkdata.b2bbase.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCompanyDto {

    private String name;
    private String ean;
    private long companyId;
    private long productId;
    private String description;
    //private List<ProductImage> images;
}
