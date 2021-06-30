<#import "../partials/main.ftlh" as main>
<@main.renderWith>
<#--<@main.renderWith scripts=["scripts/.js"]>-->
    <div class="mx-auto mt-3 w-50">
        <form id="editTodoForm">
            <#if _csrf??>
                <input type="hidden" name="${(_csrf.parameterName)!'csrf'}" value="${(_csrf.token)!'--no-token--'}"/>
            </#if>

            <h3 class="text-center">Редактировать TODO</h3>
            <div class="form-group">
                <label>Изменить название:</label>
                <input type="text" name="name" id="name" class="form-control" placeholder="Название" value="${todo.name}">
                <#if errors??>
                    <#list errors as fieldError>
                        <#if fieldError.field == 'name'>
                            <div class="alert alert-warning mt-1" role="alert">${fieldError.defaultMessage!'-- no message --'}</div>
                        </#if>
                    </#list>
                </#if>
            </div>
            <div class="form-group">
                <label>Изменить описание:</label>
                    <p><textarea name="description" id="description" class="form-control" placeholder="Описание">${todo.description}</textarea></p>
                <#if errors??>
                    <#list errors as fieldError>
                        <#if fieldError.field == 'description'>
                            <div class="alert alert-warning mt-1" role="alert">${fieldError.defaultMessage!'-- no message --'}</div>
                        </#if>
                    </#list>
                </#if>
            </div>
            <div class="form-group">
                <label>Изменить статус:</label>
                <select id="status" name="status">
                    <#list status as st>
                        <option value="${st.name()}">${st.name()}</option>
                    </#list>
                </select>
            </div>
            <button class="btn btn-primary btn-block" onclick="sendForm()">Сохранить</button>
        </form>
    </div>
</@main.renderWith>
<script>
    function sendForm() {
        var xhr = new XMLHttpRequest();
        xhr.open('PUT', window.location.href, true);
        xhr.send(new FormData(document.getElementById('editTodoForm')));
    }
</script>
