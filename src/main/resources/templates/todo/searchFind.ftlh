<#import "../partials/main.ftlh" as main />

<@main.renderWith scripts=["/js/validation.js"] styles=["https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"]>
    <h3 class="mt-3">Все TODO</h3>
    <form action="/${link}/todo/export" method="post">
        <h3>Поиск был произведен по следующим полям</h3>
        <input name="name" value="${name!''}"><input name="status" value="${status!'all'}"><input name="startDate" type="date" value="${startDate!''}"><input name="finishDate" type="date" value="${finishDate!''}">
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
                    <td><a href="/${link}/todo/open/${todo.id}">${todo.name}</a></td>
                    <td>${todo.status}</td>
                    <td><a href="/${link}/todo/delete/${todo.id}" style="color:red;">Удалить</a></td>
                    <td><a href="/${link}/todo/edit/${todo.id}" style="color:green;">Редактировать</a></td>

                </tr>
            </#list>
            </tbody>
        </table>
        <button type="submit" class="btn btn-primary btn-block">Скачать в Excel</button>
    </form>


</@main.renderWith>