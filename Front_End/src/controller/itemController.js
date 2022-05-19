

/*_________________________item part__________________________________*/

generateItemId();
loadTableItemData();


/*_________item save___________*/
$("#btnItemAdd").click(function (){

    $.ajax({url:"http://localhost:8080/Back_End/item",
        method:"POST",
        data:$("#itemForm").serialize(),
        success:function (resp){
            if (resp.status==200){
                /* clearFields();*/
                loadTableItemData();
                clearItemFields();
                generateItemId();
                loadAllItemIds();
            }else {
                alert(resp.data)
            }
        },error:function (ob,textStatus,error){
            console.log(ob);
            console.log(textStatus);
            console.log(error);
        }
    });



    /*let itemId = $("#txtItemID").val();
    let itemName = $("#txtItemName").val();
    let itemQty = $("#txtItemQty").val();
    let itemPrice = $("#txtItemPrice").val();

    var itemOB=new ItemDTO(itemId,itemName,itemQty,itemPrice);

    itemDB.push(itemOB);
    clearItemFields();
    loadTableItemData();
    generateItemId();
    loadAllItemIds();*/
});


function bindItem (){
    /*_________click Item Table ___________*/
    $("#tblItem > tr").click(function (){
        let itemId = $(this).children(":eq(0)").text();
        let itemName = $(this).children(":eq(1)").text();
        let itemQty = $(this).children(":eq(2)").text();
        let itemPrice = $(this).children(":eq(3)").text();

        /*_________set data for text fields__________*/
        $("#txtItemID").val(itemId);
        $("#txtItemName").val(itemName);
        $("#txtItemQty").val(itemQty);
        $("#txtItemPrice").val(itemPrice);

    });

};

/*_________Update Customer___________*/
$("#btnItemUpdate").click(function (){
    let itemId = $("#txtItemID").val();
    let itemName = $("#txtItemName").val();
    let itemQty = $("#txtItemQty").val();
    let itemPrice = $("#txtItemPrice").val();

    for (var i=0;i<itemDB.length;i++){
        if ( itemDB[i].getItemID()==itemId){
        itemDB[i].setItemName(itemName);
        itemDB[i].setItemQty(itemQty);
        itemDB[i].setItemPrice(itemPrice);
        }
    }
    loadTableItemData();
    clearItemFields();
    generateItemId();
});


/*_________Delete Item___________*/
function deleteItem (){
    $("#btnItemDelete").click(function (){
        let getClickItemData=$("#txtItemID").val();


        $.ajax({
            url:`http://localhost:8080/Back_End/item?itemID=${getClickItemData}`,
            method:"DELETE",
            success:function (resp){
                if (resp.status==200){
                    loadTableItemData();
                    generateItemId();
                    clearItemFields();
                }else{
                    alert(resp.data);
                }
            }
        });

    });
}


/*_________clear button___________*/
$("#btnItemClear").click(function (){
    clearItemFields();
});


/*_________customer Table Load___________*/
function loadTableItemData () {
    $("#tblItem").empty();
    $.ajax({
        url: "http://localhost:8080/Back_End/item?option=GetAll",
        method: "GET",
        success: function (rest) {
            for (const item of rest.data) {
                let raw = `<tr><td>${item.itemId}</td><td>${item.itemName}</td><td>${item.itemQty}</td><td>${item.itemPrice}</td></tr>`
                $("#tblItem").append(raw);
                bindItem();
                deleteItem();

            }
        }
    });

}
/*_________clear Item text field___________*/
function clearItemFields (){
    $("#txtItemID,#txtItemName,#txtItemQty,#txtItemPrice").val("");
}


/*_________Item Search bar___________*/
$("#btnItemSearch").click(function (){
    var searchId = $("#txtItemSearch").val();
    var response = searchItem(searchId);
    if (response){
        $("#txtItemID").val(response.getItemID());
        $("#txtItemName").val(response.getItemName());
        $("#txtItemQty").val(response.getItemQty());
        $("#txtItemPrice").val(response.getItemPrice());
    }else {
        alert("Invalid customer Search");
        clearFields();
    }
});
function searchItem (id){
    for (let i=0;i<itemDB.length;i++){
        if (itemDB[i].getItemID()==id){
            return itemDB[i];
        }
    }
}

/*_________Auto Generate Item ID___________*/
function generateItemId() {

    $.ajax({
        url:"http://localhost:8080/Back_End/item?option=GenId",
        method:"GET",
        success:function (resp){
            if (resp.status==200){
                $("#txtItemID").val(resp.data.code);
            }else{
                alert(resp.data)
            }
        }
    });


}


