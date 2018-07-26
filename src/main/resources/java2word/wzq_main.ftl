<用户名>${user.name}</用户名>
<密码>${user.pwd}</密码>
<#list user.adds as add>
<address>
    <地址>List对象address值：${add.name} </地址>
</address>
</#list>
<#list user.keyadds?keys as addkey>
<address>
    key:${addkey}
    value:${user.keyadds[addkey].name}
</address>
</#list>