package com.kacperchm.librarybackend.mapper;

import com.kacperchm.librarybackend.model.Borrow;
import com.kacperchm.librarybackend.model.BorrowToTransfer;
import com.kacperchm.librarybackend.model.User;
import com.kacperchm.librarybackend.model.UserToTransfer;

public class BorrowMapper {
    public static BorrowToTransfer mapToBorrowToTransfer(Borrow borrow) {
        return new BorrowToTransfer(
                borrow.getId(),
                borrow.getBook().getAuthor(),
                borrow.getBook().getTitle(),
                borrow.getBorrowDate(),
                borrow.getReturnDate(),
                borrow.isReturned()
                );
    }
}
