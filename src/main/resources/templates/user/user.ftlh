<#import "../partials/main.ftlh" as main>
<@main.renderWith>
<#--<@main.renderWith scripts=["scripts/.js"]>-->
    <div class="mx-auto mt-3 w-50">
        <h3 class="text-center">Добро пожаловать в систему <b>${userName}</b></h3>
        <p><a class="btn btn-primary btn-block w-100" href="/todo" role="button">Создать TODO</a></p>
        <p><a class="btn btn-primary btn-block w-100" href="/todo/todos" role="button">Просмотреть свои TODO</a></p>
        <p><a class="btn btn-primary btn-block w-100" href="/todo/search" role="button">Поиск TODO</a></p>
    </div>
</@main.renderWith>