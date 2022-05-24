package bo.custom;

import bo.SuperBO;


import javax.json.JsonArrayBuilder;

public interface OrderDetailsBo extends SuperBO {
    JsonArrayBuilder getAllOrderDetails();
}
