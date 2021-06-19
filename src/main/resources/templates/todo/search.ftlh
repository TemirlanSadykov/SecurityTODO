<#import "../partials/main.ftlh" as main>
<@main.renderWith>
<#--<@main.renderWith scripts=["scripts/.js"]>-->
    <div class="mx-auto mt-3 w-50">
        <form id="login-form" action="/${link}/todo/search" method="post">

            <#if _csrf??>
                <input type="hidden" name="${(_csrf.parameterName)!'csrf'}" value="${(_csrf.token)!'--no-token--'}"/>
            </#if>

            <h3 class="text-center">Поиск TODO</h3>
            <div class="form-group">
                <label>Выберите статус:</label>
                <select id="status" name="status">
                    <option value="all">Все</option>
                    <#list status as st>
                        <option value="${st.name()}">${st.name()}</option>
                    </#list>
                </select>
            </div>
            <div class="form-group">
                <label>Введите название:</label>
                <input type="text" name="name" class="form-control" placeholder="Название">
                <#if errors??>
                    <#list errors as fieldError>
                        <#if fieldError.field == 'name'>
                            <div class="alert alert-warning mt-1" role="alert">${fieldError.defaultMessage!'-- no message --'}</div>
                        </#if>
                    </#list>
                </#if>
            </div>
            <div class="form-group">
                <label>Выберите дату - "От":</label>
                <input type="datetime-local" name="startDate" class="form-control">
                <#if errors??>
                    <#list errors as fieldError>
                        <#if fieldError.field == 'startDate'>
                            <div class="alert alert-warning mt-1" role="alert">${fieldError.defaultMessage!'-- no message --'}</div>
                        </#if>
                    </#list>
                </#if>
            </div>
            <div class="form-group">
                <label>Выберите дату- "До":</label>
                <input type="datetime-local" name="finishDate" class="form-control">
                <#if errors??>
                    <#list errors as fieldError>
                        <#if fieldError.field == 'finishDate'>
                            <div class="alert alert-warning mt-1" role="alert">${fieldError.defaultMessage!'-- no message --'}</div>
                        </#if>
                    </#list>
                </#if>
            </div>
            <button name="register" class="btn btn-primary btn-block">Поиск</button>
        </form>
    </div>
</@main.renderWith>