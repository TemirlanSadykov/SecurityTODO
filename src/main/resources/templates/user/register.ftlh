<#import "../partials/main.ftlh" as main>
<@main.renderWith>
<#--<@main.renderWith scripts=["scripts/.js"]>-->
    <div class="mx-auto mt-3 w-50">
        <form id="login-form" action="/register" method="post">

            <#if _csrf??>
                <input type="hidden" name="${(_csrf.parameterName)!'csrf'}" value="${(_csrf.token)!'--no-token--'}"/>
            </#if>

            <h3 class="text-center">Зарегистрироваться</h3>
            <div class="form-group">
                <label>Введите Логин:</label>
                <input type="text" name="login" class="form-control" placeholder="Логин" autofocus>
                <#if errors??>
                    <#list errors as fieldError>
                        <#if fieldError.field == 'login'>
                            <div class="alert alert-warning mt-1" role="alert">${fieldError.defaultMessage!'-- no message --'}</div>
                        </#if>
                    </#list>
                </#if>
            </div>
            <div class="form-group">
                <label>Введите Email:</label>
                <input type="email" name="email" class="form-control" placeholder="Email" autofocus>
                <#if errors??>
                    <#list errors as fieldError>
                        <#if fieldError.field == 'email'>
                            <div class="alert alert-warning mt-1" role="alert">${fieldError.defaultMessage!'-- no message --'}</div>
                        </#if>
                    </#list>
                </#if>
            </div>
            <div class="form-group">
                <label>Выберите должность:</label>

                <#list role as rl>
                    <input type="radio" id="${rl.name}" name="role" value="${rl.id}">
                    <label for="${rl.name}">${rl.name}</label>
                </#list>
                <#if errors??>
                    <#list errors as fieldError>
                        <#if fieldError.field == 'role'>
                            <div class="alert alert-warning mt-1" role="alert">${fieldError.defaultMessage!'-- no message --'}</div>
                        </#if>
                    </#list>
                </#if>
            </div>
            <div class="form-group">
                <label>Введите пароль:</label>
                <input type="password" name="password" class="form-control" placeholder="Пароль">
                <#if errors??>
                    <#list errors as fieldError>
                        <#if fieldError.field == 'password'>
                            <div class="alert alert-warning mt-1" role="alert">${fieldError.defaultMessage!'-- no message --'}</div>
                        </#if>
                    </#list>
                </#if>
            </div>
            <button name="register" class="btn btn-primary btn-block">Зарегистрироваться</button>
            <div>
                <#if error?? >
                    <div class="alert alert-warning mt-1" style="color: red" role="alert">Данный пользователь уже зарегистрирован</div>
                </#if>
            </div>
        </form>
    </div>
</@main.renderWith>