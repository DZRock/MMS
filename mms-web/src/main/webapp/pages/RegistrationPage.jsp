<%--
  Created by IntelliJ IDEA.
  User: DZRock
  Date: 31.03.2016
  Time: 0:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <title>Регистрация в системе</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/core.css">
    <script   src="https://code.jquery.com/jquery-2.2.2.min.js"   integrity="sha256-36cp2Co+/62rEAAYHLmRCPIych47CvdM+uTBJwSzWjI="   crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
          integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css"
          integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
            integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
            crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/bower/moment/min/moment.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/bower/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bower/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css/">

    <script>
        $(document).ready(function(){

            $('#datetimepicker2').datetimepicker({
                format: 'DD.MM.YYYY'
            });

            $('body').on('click','#submitForm',function(){
                $('.has-error').removeClass('bg-danger');
                var valid=true;
                $('.validatable').each(function(){
                    if($(this).val().length==0){
                        $(this).closest('div').addClass('has-error');
                        valid=false;
                    }
                });

                if(valid){
                    $('#regform').submit();
                }else{
                    $('#msg').modal();
                }
            });

        });
    </script>
</head>
<body>
<div class="container">
    <div class="col-md-6 login-form">
        <form action="registration" method="post" role="form" id="regform">
            <%--<sec:csrfMetaTags/>--%>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div>
                <h2>Регистрация</h2>
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="text" name="email" id="email" class="form-control validatable">
            </div>
            <div class="form-group">
                <label for="name">ФИО:</label>
                <input type="text" name="name" id="name" class="form-control validatable">
            </div>
            <div class="form-group">
                <label for="address">Адрес:</label>
                <input type="text" name="address" id="address" class="form-control validatable">
            </div>
            <div class="form-group">
                <div class="radio">
                    <label><input type="radio" name="sex" value="1">Мужчина</label>
                </div>
                <div class="radio">
                    <label><input type="radio" name="sex" value="0">Женщина</label>
                </div>
            </div>
            <div class="form-group">
                <label for="datetimepicker2">Дата рождения:</label>
                <div class='input-group date' id='datetimepicker2'>
                    <input type='text' class="form-control validatable" name="birthDate"/>
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                </div>
            </div>
            <div class="form-group">
                <label for="password">Пароль:</label>
                <input type="password" name="password" id="password" class="form-control validatable">
            </div>

        </form>
        <div>
            <a href="#" id="submitForm" class="btn btn-default">Регистрация</a>
        </div>
    </div>

    <div id="msg" class="modal fade" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div class="modal-body">
                    Заполните все поля
                </div>
                <div class="modal-footer">
                    <a href="#" type="button" class="btn btn-default" data-dismiss="modal">Закрыть</a>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript">
        $(function () {
            $('#datetimepicker2').datetimepicker({
                locale: 'ru'
            });
        });
    </script>
</div>
</body>
</html>
