/*_________________customer part______________________*/
generateId();
loadTableCusData();


/* ____________________Validation - Start */
$('#error1').css({ "display": "none" });
$('#error2').css({ "display": "none" });
$('#error3').css({ "display": "none" });
$('#error4').css({ "display": "none" });

$('#error01').css({ "display": "none" });
$('#error02').css({ "display": "none" });
$('#error03').css({ "display": "none" });
$('#error04').css({ "display": "none" });

var RegExCusName = /^[A-z ]{5,20}$/;
var RegExCusAddress = /^[0-9/A-z. ,]{7,}$/;
var RegExCusSalary = /^[0-9]{1,}[.]?[0-9]{1,2}$/;

validation(RegExCusName, '#txtCusName', '#error2', '#txtCusAddress', '#btnCusAdd');
validation(RegExCusAddress, '#txtCusAddress', '#error3', '#txtCusSalary', '#btnCusAdd');
validation(RegExCusSalary, '#txtCusSalary', '#error4', "#btnCusAdd", '#btnCusAdd');

validation(RegExCusName, '#txtCusName', '#error02', '#txtCusAddress', '#btnCusUpdate');
validation(RegExCusAddress, '#txtCusAddress', '#error03', '#txtCusSalary', '#btnCusUpdate');
validation(RegExCusSalary, '#txtCusSalary', '#error04', '#btnCusUpdate', '#btnCusUpdate');


// Customer Validation Function - Start
function validation(regEx, id, error, nextId, btn) {
    $(id).keyup(function (event) {
        let input = $(id).val();
        if (regEx.test(input)) {
            $(id).css({ 'border': '2px solid green', 'background-color': '#fff' });
            $(error).css({ "display": "none" });
            if (event.key == "Enter") {
                $(btn).prop('disabled', false);
                $(nextId).focus();
            }
        } else {
            $(id).css({ 'border': '2px solid red', 'background-color': '#ffe6e6' });
            $(error).css({ "color": "red", "display": "block" });
            $(btn).prop('disabled', true);
        }
    });
}

/*_________customer save___________*/
$("#btnCusAdd").click(function (){

    $.ajax({url:"http://localhost:8080/Back_End/customer",
        method:"POST",
        data:$("#customerForm").serialize(),
        success:function (resp){
            if (resp.status==200){
               /* clearFields();*/
                loadTableCusData();
                generateId();
                loadAllCustomerIds();
            }else {
                alert(resp.data)
            }
        },error:function (ob,textStatus,error){
        console.log(ob);
        console.log(textStatus);
        console.log(error);
        }
    });


});

/*_________Update Customer___________*/
$("#btnCusUpdate").click(function (){

    var cusOb = {
        id: $("#txtCusID").val(),
        name: $("#txtCusName").val(),
        address: $("#txtCusAddress").val(),
        salary: $("#txtCusSalary").val()
    }

    $.ajax({
        url: "http://localhost:8080/Back_End/customer",
        method: "PUT",
        data: JSON.stringify(cusOb),
        success: function (resp) {
            if (resp.status == 200) {
                loadTableCusData();
                clearFields();
                generateId();
            } else if (resp.status == 400) {
                alert(resp.data);
            }
        }
    });

});

/*_________Delete Customer___________*/
function deleteCustomer (){
$("#btnCusDelete").click(function (){
    let getClickData=$("#txtCusID").val();

    $.ajax({
        url:`http://localhost:8080/Back_End/customer?customerID=${getClickData}`,
        method:"DELETE",
        success:function (resp){
            if (resp.status==200){
                loadTableCusData();
            }else{
                alert(resp.data);
            }
        }
    });

});
}

/*_________click customer Table ___________*/
function bindCustomer (){
    $("#customerTableBody > tr").click(function (){
        let customerId = $(this).children(":eq(0)").text();
        let customerName = $(this).children(":eq(1)").text();
        let customerAddress = $(this).children(":eq(2)").text();
        let customerSalary = $(this).children(":eq(3)").text();

        /*_________set data for text fields__________*/
        $("#txtCusID").val(customerId);
        $("#txtCusName").val(customerName);
        $("#txtCusAddress").val(customerAddress);
        $("#txtCusSalary").val(customerSalary);

    });
}

/*_________clear button___________*/
$("#btnCusClearField").click(function (){
    clearFields();
});

/*_________customer Table Load___________*/
function loadTableCusData (){
    $("#customerTableBody").empty();
    $.ajax({
       url:"http://localhost:8080/Back_End/customer?option=GetAll",
        method:"GET",
        success:function (rest){
           for (const customer of rest.data){
              /* for (var i of customerDB){*/
                   let raw = `<tr><td>${customer.id}</td><td>${customer.name}</td><td>${customer.address}</td><td>${customer.salary}</td></tr>`
                   $("#customerTableBody").append(raw);
                   bindCustomer();
                   deleteCustomer();
           }
        }
    });
   /* for (var i of customerDB){
        let raw = `<tr><td>${i.getCustomerID()}</td><td>${i.getCustomerName()}</td><td>${i.getCustomerAddress()}</td><td>${i.getCustomerSalary()}</td></tr>`
        $("#customerTableBody").append(raw);
        bindCustomer();
        deleteCustomer();
    }*/
}

/*_________clear text field___________*/
function clearFields (){
    $("#txtCusName,#txtCusAddress,#txtCusSalary").val("");
    loadTableCusData();
    generateId();
}

/*_________field focusing___________*/
$("#txtCusID").keydown(function (event) {
    if (event.key == "Enter") {
        $("#txtCusName").focus();
    }
});

$("#txtCusName").keydown(function (event) {
    if (event.key == "Enter") {
        $("#txtCusAddress").focus();
    }
});

$("#txtCusAddress").keydown(function (event) {
    if (event.key == "Enter") {
        $("#txtCusSalary").focus();
    }
});

/*_________Customer Search bar___________*/
$("#btnCusSearch").click(function (){

    if (!$("#txtCusSearch").val()) {
        loadTableCusData();
        return;
    }

   $.ajax({
        url: "http://localhost:8080/Back_End/customer?option=SEARCH",
       method: "GET",
       data: {
            id: $("#txtCusSearch").val()
        }, success: function (resp) {
            if (resp.status == 200) {
                $("#customerTableBody").empty();
                for (const customer of resp.data) {
                    let row = `<tr><td>${customer.id}</td><td>${customer.name}</td><td>${customer.address}</td><td>${customer.salary}</td></tr>`;
                    $("#customerTableBody").append(row);
                    bindCustomer();
                }
            } else {
                alert(resp.data);
                loadTableCusData(); //load all customers
                clearFields();   //Clear Input Fields
            }
        }
    });

   /* var searchId = $("#txtCusSearch").val();
    var response = searchCustomer(searchId);
    if (response){
        $("#txtCusID").val(response.getCustomerID());
        $("#txtCusName").val(response.getCustomerName());
        $("#txtCusAddress").val(response.getCustomerAddress());
        $("#txtCusSalary").val(response.getCustomerSalary());
    }else {
        alert("Invalid customer Search");
        clearFields();
    }*/

});


/*function searchCustomer (id){
    for (let i=0;i<customerDB.length;i++){
        if (customerDB[i].getCustomerID()==id){
            return customerDB[i];
        }
    }
}*/


/*_________Auto Generate ID___________*/
function generateId() {

    $.ajax({
        url:"http://localhost:8080/Back_End/customer?option=GenId",
        method:"GET",
        success:function (resp){
            if (resp.status==200){
                $("#txtCusID").val(resp.data.id);
            }else{
                alert(resp.data)
            }
        }
    });
}
