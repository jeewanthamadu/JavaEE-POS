package servlet;

import bo.BOFactory;
import bo.custom.impl.ItemBOImpl;

import dto.ItemDTO;

import javax.annotation.Resource;
import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;


@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {

    ItemBOImpl itemBO = (ItemBOImpl) BOFactory.getBoFactory().getBO(BOFactory.BoTypes.ITEM);

    @Resource(name = "java:comp/env/jdbc/pool")
    public static DataSource ds;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        JsonObjectBuilder dataMsgBuilder = Json.createObjectBuilder();
        PrintWriter writer = resp.getWriter();

     /*   Connection connection = null;*/

        try {
//            connection = ds.getConnection();
            String option = req.getParameter("option");
            switch (option) {
                case "GetAll":
                    resp.setStatus(HttpServletResponse.SC_OK);//201
                    /*ResultSet resultSet = connection.prepareStatement("select * from item").executeQuery();
                    while (resultSet.next()) {
                        String id = resultSet.getString(1);
                        String name = resultSet.getString(2);
                        int qty = resultSet.getInt(3);
                        double price = resultSet.getDouble(4);

                        resp.setStatus(HttpServletResponse.SC_OK);//201

                        objectBuilder.add("itemId", id);
                        objectBuilder.add("itemName", name);
                        objectBuilder.add("itemQty", qty);
                        objectBuilder.add("itemPrice", price);

                        arrayBuilder.add(objectBuilder.build());
                    }
*/
                    dataMsgBuilder.add("data", itemBO.getAllItems());
                    dataMsgBuilder.add("massage", "Done");
                    dataMsgBuilder.add("status", "200");

                    writer.print(dataMsgBuilder.build());
                    break;


                case "GenId":
                    /*ResultSet rst = connection.prepareStatement("SELECT code FROM item ORDER BY code DESC LIMIT 1").executeQuery();
                    if (rst.next()) {
                        int tempId = Integer.parseInt(rst.getString(1).split("-")[1]);
                        tempId+=1;
                        if (tempId < 10) {
                            objectBuilder.add("code", "I00-00" + tempId);
                        } else if (tempId < 100) {
                            objectBuilder.add("code", "I00-0" + tempId);
                        } else if (tempId < 1000) {
                            objectBuilder.add("code", "I00-" + tempId);
                        }
                    }else{
                        objectBuilder.add("code", "I00-000");
                    }*/
                    dataMsgBuilder.add("data",itemBO.generateItemID());
                    dataMsgBuilder.add("message","Done");
                    dataMsgBuilder.add("status",200);
                    writer.print(dataMsgBuilder.build());

                    break;

                case "SEARCH":
                    String id = req.getParameter("id");
                    resp.setStatus(HttpServletResponse.SC_OK);//201
                    dataMsgBuilder.add("data", itemBO.searchItem(id));
                    dataMsgBuilder.add("massage", "Done");
                    dataMsgBuilder.add("status", "200");

                    writer.print(dataMsgBuilder.build());
                    break;


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } /*finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }*/

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String id = req.getParameter("itemId");
        String name = req.getParameter("itemName");
        String qty = req.getParameter("itemQty");
        String price = req.getParameter("itemPrice");

        ItemDTO itemDTO = new ItemDTO(id, name,Integer.parseInt(qty), Double.parseDouble(price));

        PrintWriter writer = resp.getWriter();

       /* Connection connection=null;*/
        try {
            /*connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO item VALUES(?,?,?,?)");
            pstm.setObject(1,id);
            pstm.setObject(2,name);
            pstm.setObject(3,qty);
            pstm.setObject(4,price);
*/
            if ( itemBO.addItem(itemDTO)){
                JsonObjectBuilder response = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_CREATED);
                response.add("status",200);
                response.add("message","Added SuccessFully");
                response.add("data","");

                writer.print(response.build());
            }

        }catch (SQLException throwables){

            JsonObjectBuilder response = Json.createObjectBuilder();
            resp.setStatus(HttpServletResponse.SC_OK);
            response.add("status",400);
            response.add("message","Error");
            response.add("data",throwables.getLocalizedMessage());

            writer.print(response.build());

            throwables.printStackTrace();

        }/*finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }*/

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String itemID = req.getParameter("itemID");
        JsonObjectBuilder dataMsgBuilder = Json.createObjectBuilder();
        PrintWriter writer = resp.getWriter();

        /*Connection connection = null;*/
        try {
           /* connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM item WHERE code=?");
            pstm.setObject(1, itemID);
*/
            if (itemBO.deleteItem(itemID)) {
                resp.setStatus(HttpServletResponse.SC_OK); //200
                dataMsgBuilder.add("data", "");
                dataMsgBuilder.add("massage", "Item Deleted");
                dataMsgBuilder.add("status", "200");
                writer.print(dataMsgBuilder.build());
            }
        } catch (SQLException e) {
            dataMsgBuilder.add("status", 400);
            dataMsgBuilder.add("message", "Error");
            dataMsgBuilder.add("data", e.getLocalizedMessage());
            writer.print(dataMsgBuilder.build());
            resp.setStatus(HttpServletResponse.SC_OK); //200
        }
        /*finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();

        String itemIDUpdate = jsonObject.getString("itemId");
        String itemNameUpdate = jsonObject.getString("itemName");
        String itemQtyUpdate = jsonObject.getString("itemQty");
        String itemPriceUpdate = jsonObject.getString("itemPrice");

        ItemDTO itemDTO = new ItemDTO(itemIDUpdate, itemNameUpdate,Integer.parseInt(itemQtyUpdate) , Double.parseDouble(itemPriceUpdate));


        PrintWriter writer = resp.getWriter();

        /*Connection connection = null;*/
        try {
            /*connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("UPDATE item SET description=?, qtyOnHand=?, unitPrice=? WHERE code=?");
            pstm.setObject(1, itemNameUpdate);
            pstm.setObject(2, itemQtyUpdate);
            pstm.setObject(3, itemPriceUpdate);
            pstm.setObject(4, itemIDUpdate);*/

            if (itemBO.updateItem(itemDTO)) {
                JsonObjectBuilder response = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_CREATED);//201
                response.add("status", 200);
                response.add("message", "Successfully Updated");
                response.add("data", "");
                writer.print(response.build());
            }

        } catch (SQLException e) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", 400);
            response.add("message", "Error");
            response.add("data", e.getLocalizedMessage());
            writer.print(response.build());
            resp.setStatus(HttpServletResponse.SC_OK); //200
        } /*finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/
    }




}
