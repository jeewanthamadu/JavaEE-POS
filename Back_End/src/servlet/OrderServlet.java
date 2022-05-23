package servlet;

import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@WebServlet(urlPatterns = "/order")
public class OrderServlet extends HttpServlet {
    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource ds;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        JsonObjectBuilder dataMsgBuilder = Json.createObjectBuilder();
        PrintWriter writer = resp.getWriter();

        Connection connection=null;

        try {
            connection= ds.getConnection();
            ResultSet rst;
            PreparedStatement pstm;

            String option = req.getParameter("option");
            switch (option){
                case "Load_Item_Id":
                    rst  = connection.prepareStatement("SELECT code FROM item").executeQuery();
                    while (rst.next()){
                        String code = rst.getString(1);
                        objectBuilder.add("code",code);
                        arrayBuilder.add(objectBuilder.build());
                    }
                    dataMsgBuilder.add("data",arrayBuilder.build());
                    dataMsgBuilder.add("message","Done");
                    dataMsgBuilder.add("status",200);

                    writer.print(dataMsgBuilder.build());
                    break;


                case "Load_cus_Id":
                    rst  = connection.prepareStatement("SELECT id FROM customer").executeQuery();
                    while (rst.next()){
                        String id = rst.getString(1);
                        objectBuilder.add("id",id);
                        arrayBuilder.add(objectBuilder.build());
                    }
                    dataMsgBuilder.add("data",arrayBuilder.build());
                    dataMsgBuilder.add("message","Done");
                    dataMsgBuilder.add("status",200);

                    writer.print(dataMsgBuilder.build());
                    break;


                case "selectedCusData":
                    String cusId = req.getParameter("cusId");
                    pstm = connection.prepareStatement("SELECT * FROM customer WHERE id=?");
                    pstm.setObject(1,cusId);
                    rst = pstm.executeQuery();
                    if (rst.next()){
                        String cusName = rst.getString(2);
                        String cusAddress = rst.getString(3);
                        String cusSalary = rst.getString(4);
                        objectBuilder.add("cusName",cusName);
                        objectBuilder.add("cusAddress",cusAddress);
                        objectBuilder.add("cusSalary",cusSalary);

                        arrayBuilder.add(objectBuilder.build());
                    }
                    dataMsgBuilder.add("data",arrayBuilder.build());
                    dataMsgBuilder.add("message","Done");
                    dataMsgBuilder.add("status",200);

                    writer.print(dataMsgBuilder.build());
                    break;

            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
            dataMsgBuilder.add("data",throwables.getLocalizedMessage());
            dataMsgBuilder.add("message","Error");
            dataMsgBuilder.add("status",400);
            writer.print(dataMsgBuilder.build());
        }
        finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();

            }

        }

    }





}
