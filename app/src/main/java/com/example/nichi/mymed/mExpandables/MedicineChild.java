package com.example.nichi.mymed.mExpandables;

public class MedicineChild {
    private String Quantity;
    private String Usage;
    private String Comment;

    public MedicineChild(String quantity, String comment) {
        Quantity = quantity;
        Comment = comment;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
