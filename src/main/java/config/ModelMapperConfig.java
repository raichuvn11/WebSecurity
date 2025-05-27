package config;

<<<<<<< HEAD
import org.modelmapper.ModelMapper;
=======
>>>>>>> master

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ModelMapperConfig implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Khởi tạo ModelMapper và lưu trữ vào ServletContext
<<<<<<< HEAD
        ModelMapper modelMapper = new ModelMapper();
=======
        ModelMapperConfig modelMapper = new ModelMapperConfig();
>>>>>>> master
        ServletContext context = sce.getServletContext();
        context.setAttribute("modelMapper", modelMapper);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Giải phóng tài nguyên nếu cần
    }
}
