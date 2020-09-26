package dto.orderDTO;

import java.util.List;
import java.util.Objects;

public class OffersDiscountDTO {
    private String condition;
    private List<OfferDiscountDTO> offersDiscount;


    public OffersDiscountDTO(String condition, List<OfferDiscountDTO> offersDiscount) {
        this.condition = condition;
        this.offersDiscount = offersDiscount;
    }

    public void setOffersDiscount(List<OfferDiscountDTO> offersDiscount) {
        this.offersDiscount = offersDiscount;
    }

    public String getCondition() {
        return condition;
    }

    public List<OfferDiscountDTO> getOffersDiscount() {
        return offersDiscount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OffersDiscountDTO)) return false;
        OffersDiscountDTO that = (OffersDiscountDTO) o;
        return Objects.equals(condition, that.condition) &&
                Objects.equals(offersDiscount, that.offersDiscount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(condition, offersDiscount);
    }


}
