<#import "../partials/main.ftlh" as main />

<@main.renderWith scripts=["/js/validation.js"] styles=["https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"]>
    <h3 class="mt-3">Все TODO</h3>
    <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Дата создания</th>
            <th scope="col">Название</th>
            <th scope="col">Статус</th>
            <th scope="col">Удаление</th>
            <th scope="col">Редактирование</th>
        </tr>
        </thead>
        <tbody>
        <#list items as todo>

                    <tr>
                        <th scope="row">${todo?counter}</th>
                        <td>${todo.date.format('dd-MMM-yyyy')}</td>
                        <td><a href="/user/todo/open/${todo.id}">${todo.name}</a></td>
                        <td>${todo.status}</td>
                        <td><a href="/user/todo/delete/${todo.id}" style="color:red;">Удалить</a></td>
                        <td><a href="/user/todo/edit/${todo.id}" style="color:green;">Редактировать</a></td>

                    </tr>
        </#list>
        </tbody>
    </table>
    <a type="button" сlass="btn btn-primary btn-block" href="/user/todo/export">Скачать в Excel</a>


</@main.renderWith>