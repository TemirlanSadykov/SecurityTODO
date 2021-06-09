<#import "../partials/main.ftlh" as main />

<@main.renderWith scripts=["/js/validation.js"] styles=["https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"]>
    <h3 class="mt-3">Все пользоваетли</h3>
    <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Login</th>
            <th scope="col">Email</th>
            <th scope="col">Активирован</th>
        </tr>
        </thead>
        <tbody>
        <#list items as user>

                    <tr>
                        <th scope="row">${user?counter}</th>
                        <td>${user.login}</td>
                        <td>${user.email}</td>
                        <td><a href="/admin/users/activate/${user.id}">${user.activate?string("Да", "Нет")}</a></td>
                    </tr>
        </#list>

        </tbody>
    </table>
    <#include "../partials/pagination.ftlh">

</@main.renderWith>