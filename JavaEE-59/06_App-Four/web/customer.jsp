<%@ page import="java.util.ArrayList" %>
<%@ page import="model.CustomerDTO" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%--
  Created by IntelliJ IDEA.
  User: sanu
  Date: 2022-11-14
  Time: 2:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Customer Manage</title>
    <meta content="width=device-width initial-scale=1" name="viewport">
    <link href="assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/css/styles.css" rel="stylesheet">
    <link crossorigin="anonymous" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css"
          integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" rel="stylesheet">
    <style>
        ul > li {
            cursor: pointer;
        }
    </style>
</head>
<body>

<%

    ArrayList<CustomerDTO> allCustomers = (ArrayList<CustomerDTO>) request.getAttribute("customers");
//    ArrayList<CustomerDTO> allCustomers= new ArrayList();


//
//    allCustomers.add(new CustomerDTO("C002","Kamal","Panadura",2000));
//    allCustomers.add(new CustomerDTO("C003","Tamalsha","Galle",3000));
//    allCustomers.add(new CustomerDTO("C004","Ranjith","Colombo",4000));

%>

<!--header-->
<header class="jumbotron bg-primary text-white p-3">
    <h1 class="position-absolute" id="nav"></h1>
    <ul class="list-group list-group-horizontal text-danger justify-content-end font-weight-bold">
        <li class="list-group-item bg-white" id="lnkHome"><a href="index.html">Home</a></li>
        <li class="list-group-item bg-danger text-white" id="lnkCustomer"><a class="text-white" href="customer.jsp">Customer</a>
        </li>
        <li class="list-group-item bg-white" id="lnkItem"><a href="item.html">Item</a></li>
        <li class="list-group-item bg-white" id="lnkOrders"><a href="purchase-order.html">Orders</a></li>
    </ul>
</header>

<!--customer content-->
<main class="container-fluid" id="customerContent">
    <section class="row">
        <div class="col-4">
            <h1>Customer Registraion</h1>
            <form id="customerForm">
                <div class="form-group">
                    <label for="txtCustomerID">Customer ID</label>
                    <input class="form-control" id="txtCustomerID" type="text" name="id">
                    <span class="control-error" id="lblcusid"></span>
                </div>
                <div class="form-group">
                    <label for="txtCustomerName">Customer Name</label>
                    <input class="form-control" id="txtCustomerName" type="text" name="name">
                    <span class="control-error" id="lblcusname"></span>
                </div>
                <div class="form-group">
                    <label for="txtCustomerAddress">Customer Address</label>
                    <input class="form-control" id="txtCustomerAddress" type="text" name="address">
                    <span class="control-error" id="lblcusaddress"></span>
                </div>
                <div class="form-group">
                    <label for="txtCustomerSalary">Customer Salary</label>
                    <input class="form-control" id="txtCustomerSalary" type="text" name="salary">
                    <span class="control-error" id="lblcussalary"></span>
                </div>
            </form>
            <div class="btn-group">
                <button class="btn btn-primary" id="btnCustomer" type="button">Save Customer</button>
                <button class="btn btn-danger" id="btnCusDelete" type="button">Remove</button>
                <button class="btn btn-warning" id="btnUpdate" type="button">Update</button>
                <button class="btn btn-success" id="btnGetAll" type="button">Get All</button>
                <button class="btn btn-danger" id="btn-clear1" type="button">Clear All</button>
            </div>

        </div>
        <div class="col-8">
            <table class="table table-bordered table-hover">
                <thead class="bg-danger text-white">
                <tr>
                    <th>Customer ID</th>
                    <th>Customer Name</th>
                    <th>Customer Address</th>
                    <th>Customer Salary</th>
                </tr>
                </thead>
                <tbody id="tblCustomer">

                </tbody>
            </table>
        </div>
    </section>
</main>


<script src="assets/js/jquery-3.6.1.min.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
<script>


    function bindRowClickEvents() {
        $("#tblCustomer>tr").click(function () {
            let id = $(this).children(":eq(0)").text();
            let name = $(this).children(":eq(1)").text();
            let address = $(this).children(":eq(2)").text();
            let salary = $(this).children(":eq(3)").text();
            // console.log(id, name, address, salary);

            //setting table details values to text fields
            $('#txtCustomerID').val(id);
            $('#txtCustomerName').val(name);
            $('#txtCustomerAddress').val(address);
            $('#txtCustomerSalary').val(salary);

        });
    }
    //

    function setTextfieldValues(id, name, address, salary) {
        $("#txtCustomerID").val(id);
        $("#txtCustomerName").val(name);
        $("#txtCustomerAddress").val(address);
        $("#txtCustomerSalary").val(salary);
    }




    // $("#btnCustomer").click(function(){
    //
    //     let customerID = $("#txtCustomerID").val();
    //     let customerName = $("#txtCustomerName").val();
    //     let customerAddress = $("#txtCustomerAddress").val();
    //     let customerSalary = $("#txtCustomerSalary").val();
    //
    //     //send ajax request to the customer servlet
    //     $.ajax({
    //         url:"customer?id="+customerID+"&name="+customerName,
    //     });
    // });




    //add customer to the database
    $("#btnCustomer").click(function () {

        var formData = $("#customerForm").serialize();
        // will generate a query String including form data

        //send ajax request to the customer servlet
        $.ajax({
            url: "customer?option=add",
            method: "post",
            data: formData,
            success:function (){
                loadAllCustomers();
            }
        });
    });

    //get all customers from the database
    $("#btnGetAll").click(function () {
        //send ajax request to the customer servlet
        loadAllCustomers();
    });


    //delete customer by id
    $("#btnCusDelete").click(function(){
        let id=$("#txtCustomerID").val();
        $.ajax({
            url:"customer?id="+id+"&option=delete",
            method:"post",
            success:function(){
                loadAllCustomers();
            }
        });
    });

    //update customer details
    $("#btnUpdate").click(function(){

        var formData = $("#customerForm").serialize();
        $.ajax({
            url:'customer?option=update',
            method:'post',
            data:formData,
            success:function (){
                loadAllCustomers();
            }

        });
    });

    loadAllCustomers();

    //load all
    function loadAllCustomers(){
        $("#tblCustomer").empty();
        $.ajax({
            url: "customer",
            dataType:"json",
            success: function (resp) {
                console.log(resp);
                for (let cus of resp) {
                    var row='<tr><td>'+cus.id+'</td><td>'+cus.name+'</td><td>'+cus.address+'</td><td>'+cus.salary+'</td></tr>';
                    $("#tblCustomer").append(row);
                }
                bindRowClickEvents();
            }
        });

    }

</script>
</body>
</html>
