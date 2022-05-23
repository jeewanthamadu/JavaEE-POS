

generateOrderId();
loadAllCustomerIds();
loadAllItemIds();
setDate();
var selectedCustomerId;
var selectedItemId;
$("#dis").val(0);



$("#btnOrderAdd").click(function (){
    addCart();
});
$("#btnOrderPurchase").click(function (){
    purchaseOrder();
});


$("#dis").keyup(function(event){
        discountCal();

});

$("#ocash").keyup(function (event) {
        let subTotal = parseInt($("#lblFullTotal").text());
        let cash = parseInt($("#ocash").val());
        let balance = cash - subTotal;
        $("#balance").val(balance);
});

/*_________load customer and item ids___________*/
$("#orderCusIdCmb").change(function (e){
    selectedCustomerId =$('#orderCusIdCmb').find(":selected").text();
    selectedCustomer(selectedCustomerId);
});

$("#itemIdCmb").change(function (e){
    selectedItemId =$('#itemIdCmb').find(":selected").text();
    selectedItem(selectedItemId);
});



/*_________Auto Generate Order ID___________*/
function generateOrderId() {
    let index = orderDB.length - 1;
    let id;
    let temp;
    if (index != -1) {
        id = orderDB[orderDB.length - 1].getOrderID();
        temp = id.split("-")[1];
        temp++;
    }

    if (index == -1) {
        $("#txtOrderId").val("O00-001");
    } else if (temp <= 9) {
        $("#txtOrderId").val("O00-00" + temp);
    } else if (temp <= 99) {
        $("#txtOrderId").val("O00-0" + temp);
    } else {
        $("#txtOrderId").val("O00-" + temp);
    }

}


/*_________load customer ids to cmb ___________*/
function loadAllCustomerIds() {
    $("#orderCusIdCmb").empty();

    let cusIdNoneSelect = `<option disabled selected>Select Customer ID</option>`;
    $("#orderCusIdCmb").append(cusIdNoneSelect);

    $.ajax({
        url: "http://localhost:8080/Back_End/order?option=Load_cus_Id",
        method: "GET",
        data: $("#customerForm").serialize(),
        success: function (resp) {
            if (resp.status == 200) {
                for (const customer of resp.data) {
                    let option = `<option value="${customer.id}">${customer.id}</option>`;
                    $("#orderCusIdCmb").append(option);
                }

            }else {
                alert(resp.data)
            }
        }
        })

    }

/* __________________________load item ids to cmb ______________________*/
function loadAllItemIds() {
    $("#itemIdCmb").empty();

    let itemIdNoneSelect = `<option disabled selected>Select Customer ID</option>`;
    $("#itemIdCmb").append(itemIdNoneSelect);

    $.ajax({
        url: "http://localhost:8080/Back_End/order?option=Load_Item_Id",
        method: "GET",
        data: $("#itemIdCmb").serialize(),
        success: function (resp) {
            if (resp.status == 200) {
                for (const item of resp.data) {
                    let option = `<option value="${item.code}">${item.code}</option>`;
                    $("#itemIdCmb").append(option);
                }

            }else {
                alert(resp.data)
            }
        }
       })

}

/*__________________load item data to text fields___________________*/
function selectedItem(ItemId) {

    $.ajax({
        url: `http://localhost:8080/Back_End/order?option=selectedItemData&itemId=${ItemId}`,
        method: "GET",
        success: function (resp) {
            if (resp.status == 200) {
                for (const item of resp.data) {
                    $("#txtOrderItemName").val(item.itemName);
                    $("#txtQtyOnHand").val(item.itemQtyOnHand);
                    $("#txtPrice").val(item.itemPrice);
                }

            }else {
                alert(resp.data)
            }
        }
    })


}

/*_________________load customer data to text fields________________*/
function selectedCustomer(CustomerId){

    $.ajax({
        url: `http://localhost:8080/Back_End/order?option=selectedCusData&cusId=${CustomerId}`,
        method: "GET",
        /*data: $("#itemIdCmb").serialize(),*/
        success: function (resp) {
           if (resp.status == 200) {
                for (const customer of resp.data) {
                    $("#txtOCusName").val(customer.cusName);
                    $("#txtAddress").val(customer.cusAddress);
                    $("#txtSalary").val(customer.cusSalary);
                }

            }else {
                alert(resp.data)
            }
        }
    })


}

/*__________set Date____________*/
function setDate() {
    let d = new Date();
    let dd = d.toISOString().split("T")[0].split("-");
    $("#txtOrderDate").val(dd[0]+"-"+dd[1]+"-"+dd[2]);
    $("#txtOrderDate").text(dd[0]+"-"+dd[1]+"-"+dd[2]);
}



// ________________________add to cart_____________________

var fullTotal =0;

function addCart(){
    let itemCode =selectedItemId;
    let itemName=$("#txtOrderItemName").val();
    let qtyOnHand=parseInt($("#txtQtyOnHand").val());
    let price=$("#txtPrice").val();
    let orderQty=parseInt($("#txtOrderQty").val());
    let total= 0;


    if (qtyOnHand+1 <= orderQty) {
        alert("Enter Valid QTY");
        $("#txtOrderQty").val("");
        return;
    }
    qtyOnHand = qtyOnHand - orderQty;


    //___________________Updating qty_______________

    for (let i = 0; i < itemDB.length; i++) {
        if (itemCode == itemDB[i].getItemID()) {
            itemDB[i].setItemQty(qtyOnHand);
        }
    }

    let newQty = 0;
    let newTotal= 0;

    if (checkDuplicates(itemCode)==-1) {
        total = orderQty * price;
        fullTotal = fullTotal + total;
        let row =
            `<tr><td>${itemCode}</td><td>${itemName}</td><td>${price}</td><td>${orderQty}<td>${total}</td></tr>`;
        $("#orderTbody").append(row);
        $("#lblTotal").text(fullTotal+" LKR");
        $("#lblFullTotal").text(fullTotal+" LKR");
        clearInputItems();

    }else{

        let rowNo = checkDuplicates(itemCode);
        newQty = orderQty;
        let oldQty = parseInt($($('#orderTbody>tr').eq(rowNo).children(":eq(3)")).text());
        let oldTotal = parseInt($($('#orderTbody>tr').eq(rowNo).children(":eq(4)")).text());

        fullTotal = fullTotal-oldTotal;
        newQty = parseInt(oldQty) + parseInt(newQty) ;
        newTotal = newQty * price;
        fullTotal = fullTotal + newTotal;

        // ________________Update row____________________
        $('#orderTbody tr').eq(rowNo).children(":eq(3)").text(newQty);
        $('#orderTbody tr').eq(rowNo).children(":eq(4)").text(newTotal);

        $("#lblTotal").text(fullTotal+" LKR");
        $("#lblFullTotal").text(fullTotal+" LKR");
        clearInputItems();
    }

}


/* ______________Check Duplicate Item _____________________*/
function checkDuplicates(itemId) {
    for (let i = 0; i < $("#orderTbody> tr").length; i++) {
        if (itemId == $('#orderTbody').children().eq(i).children().eq(0).text()) {
            // alert(i);
            return i;
        }
    }
    return -1;
}

/* ________Clear Input Field on Selected Item Area ______________*/
function clearInputItems() {
    $("#itemIdCmb").val("");
    $("#txtOrderItemName").val("");
    $("#txtQtyOnHand").val("");
    $("#txtPrice").val("");
    $("#txtOrderQty").val("");
}

/* ________Discount ______________*/
function discountCal() {
    /*var discount =0;
    var discounted_price=0;
    var tempDiscount=0;

    discount = parseInt($("#dis").val());
    tempDiscount = 100-discount;
    discounted_price = (tempDiscount*fullTotal)/100;
    console.log(typeof discounted_price);
    $("#lblFullTotal").text(discounted_price +" LKR");
*/
    var discount = 0 ;
    var discounted_price=0;

    discount = parseInt($("#dis").val());
    discounted_price = parseInt((fullTotal - (fullTotal * discount / 100)));
    console.log(typeof discounted_price);
    $("#lblFullTotal").text(discounted_price);
}




/* ________Purchase ______________*/
function purchaseOrder() {

    let orderId = $("#txtOrderId").val();
    let customer = selectedCustomerId;
    let orderDate = $("#txtOrderDate").val();
    let discount = parseInt($("#dis").val());
    let total = $("#lblTotal").text();
    let subTotal = $("#lblFullTotal").text();

    var orderObj = new OrderDTO(orderId,customer,orderDate,discount,total,subTotal);
    orderDB.push(orderObj);

    for (let i = 0; i < $('#orderTbody tr').length; i++) {

        tblItemId = $('#orderTbody').children().eq(i).children().eq(0).text();
        tblItemName = $('#orderTbody').children().eq(i).children().eq(1).text();
        tblItemPrice = $('#orderTbody').children().eq(i).children().eq(2).text();
        tblItemQty = $('#orderTbody').children().eq(i).children().eq(3).text();
        tblItemTotal = $('#orderTbody').children().eq(i).children().eq(4).text();

        var orderDetailObj = new OrderDetailDTO(orderId,tblItemId,tblItemName,tblItemPrice,tblItemQty,tblItemTotal);
        orderDetailDB.push(orderDetailObj);
    }

}