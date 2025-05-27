package controller;

import business.Feedback;
import business.Furniture;
import data.FurnitureDB;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.beans.Customizer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@WebServlet(name = "FeedBackServlet", value = "/feedbackServlet")
public class FeedBackServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "/KhachHang/FB.jsp";
        List<Feedback> listFeedBack = FurnitureDB.getFeedBacks();

        if (listFeedBack != null) {
            for (Feedback feedBack : listFeedBack) {
                List<Furniture> listFurniture = feedBack.getOrder().getListFurniture();
                List<Furniture> uniqueFurnitureList = new ArrayList<>();
                Set<Long> seenCategoryIds = new HashSet<>();

                for (Furniture furniture : listFurniture) {
                    if (seenCategoryIds.add(furniture.getCategory().getId())) {
                        // Nếu category.id chưa tồn tại trong Set, thêm Furniture vào danh sách kết quả
                        uniqueFurnitureList.add(furniture);
                    }
                }

                feedBack.getOrder().setListFurniture(uniqueFurnitureList);

            }
        }


        request.setAttribute("listFeedBack", listFeedBack);
        request.getRequestDispatcher(url).forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
