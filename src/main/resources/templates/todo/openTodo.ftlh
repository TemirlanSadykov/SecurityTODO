<#import "../partials/main.ftlh" as main>
<@main.renderWith>
<#--<@main.renderWith scripts=["scripts/.js"]>-->
    <div class="mx-auto mt-3 w-50">

            <h3 class="text-center">TODO:</h3>
            <div class="form-group">
                <label><h3>Дата создания:</h3></label>
                <p>${todo.date.format('dd-MMM-yyyy')}</p>
                <label><h3>Срок:</h3></label>
                <p>${todo.term.format('dd-MMM-yyyy HH:mm')}</p>
            </div>
            <div class="form-group">
                <label><h3>Статус:</h3></label>
                <p>${todo.status}</p>
            </div>
            <div class="form-group">
                <label><h3>Название:</h3></label>
                <p>${todo.name}</p>
            </div>
            <div class="form-group">
                <label><h3>Описание:</h3></label>
                <p>${todo.description}</p>
            </div>
            <a href="/todo/todos" class="btn btn-primary btn-block">Выйти</a>

    </div>
</@main.renderWith>