<#import "../partials/main.ftlh" as main>
<@main.renderWith>
<#--<@main.renderWith scripts=["scripts/.js"]>-->
    <div class="mx-auto mt-3 w-50">
        <h3 class="text-center">Добро пожаловать в систему <b>${userName}</b></h3>
        <p><a class="btn btn-primary btn-block w-100" href="/admin/users" role="button">Список пользователей</a></p>
        <p><a class="btn btn-primary btn-block w-100" href="/admin/todo" role="button">Создать TODO</a></p>
        <p><a class="btn btn-primary btn-block w-100" href="/admin/todos" role="button">Просмотреть свои TODO</a></p>

    </div>

</@main.renderWith>