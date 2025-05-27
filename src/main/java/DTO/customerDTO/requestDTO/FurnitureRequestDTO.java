package DTO.customerDTO.requestDTO;

public class FurnitureRequestDTO {
    private String categoryName;
    private Long priceStart;
    private Long priceEnd;
    private Long CustomerId;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getPriceStart() {
        return priceStart;
    }

    public void setPriceStart(Long priceStart) {
        this.priceStart = priceStart;
    }

    public Long getPriceEnd() {
        return priceEnd;
    }

    public void setPriceEnd(Long priceEnd) {
        this.priceEnd = priceEnd;
    }

    public Long getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(Long customerId) {
        CustomerId = customerId;
    }
}
