// layui方法
layui.use(['tree', 'table', 'vip_table', 'layer', 'form'], function () {

    // 操作对象
    var layer = layui.layer
        , $ = layui.jquery
        , form = layui.form;
    //树状最后末端选中变色
    $("body").on("mousedown", ".layui-tree a", function () {

        $(".layui-tree a cite").css('color', '#333');
        $(this).find('cite').css('color', 'rgb(0,0,255)');
        // if (!$(this).siblings('ul').length) {
        //     $(".layui-tree a cite").css('color', '#333');
        //     $(this).find('cite').css('color', 'rgb(0,0,255)');
        // } else {
        //     $(".layui-tree a cite").css('color', '#333');
        //     $(this).find('cite').css('color', 'rgb(0,0,255)');
        // }
    });

    //初始化菜单树
    intoAjax()

    //初始化菜单树方法
    function intoAjax() {
        $.ajax({
            url: "/menu/allTree",
            type: "post",
            success: function (r) {
                itoTree(r)
            },
            error: function (request) {
                layer.alert("Connection error");
            },
        });
    }

    //生成菜单树
    function itoTree(dataTree) {
        // 树
        layui.tree({
            elem: '#tree' //传入元素选择器
            , nodes: dataTree
            , click: function (node) {
                // console.log(node)
                getMenu(node.id)
            }
        });
    }

    //从新渲染树
    function treeLoad() {
        $("#tree").html("")
        intoAjax();
    }

    //获取目标菜单数据
    function getMenu(id) {
        $.ajax({
            url: "/menu/" + id,
            type: "get",
            success: function (menuData) {
                get(menuData)
            },
            error: function (request) {
                layer.alert("Connection error");
            },
        });
    }

    // you code ...
    //查看
    function get(menuData) {
        //输入框赋值
        $("#parentId").val(menuData.parentId)
        $("#menuId").val(menuData.menuId)
        $("#name").val(menuData.name)
        $("#permission").val(menuData.permission)
        $("#icon").val(menuData.icon)
        $("#menuRrl").val(menuData.url)
        $("#sort").val(menuData.sort)
        $("#component").val(menuData.component)
        $("#menuPath").val(menuData.path)


        //下拉框赋值
        $("#method").val(menuData.method)
        var method = 'dd[lay-value=' + menuData.method + ']';
        var type = 'dd[lay-value=' + menuData.type + ']';
        if (menuData.method) {
            $('#menuMethod').siblings("div.layui-form-select").find('dl').find(method).click();
        } else {
            $('#menuMethod').siblings("div.layui-form-select").find('dl').find('dd.layui-select-tips').click();
        }
        if (menuData.type) {
            $('#menuType').siblings("div.layui-form-select").find('dl').find(type).click();
        } else {
            $('#menuType').siblings("div.layui-form-select").find('dl').find('dd.layui-select-tips').click();
        }


    }

    //按钮显隐
    function buttonDisplay(type) {
        if (type == "add") {
            $("#btnAddForm").show()
            $("#btnEditForm").hide()
            $("#btnCancel").show()
        } else if (type == "edit") {
            //按钮
            $("#btnAddForm").hide()
            $("#btnEditForm").show()
            $("#btnCancel").show()
        } else if (type == "cancel") {
            //按钮
            $("#btnAddForm").hide()
            $("#btnEditForm").hide()
            $("#btnCancel").hide()
        }
    }


    //新增节点
    $('#btnAdd').on('click', function () {
        console.log("新增节点")
        //输入框赋值
        $("#menuFrom input").val("")
        $("#parentId").val(-1)
        buttonDisplay("add");
    });

    //新增子节点
    $('#btn-child-node').on('click', function () {
        console.log("新增子节点")
        //输入框赋值
        var parentId = $("#menuId").val()
        if (parentId) {
            $("#menuFrom input").val("")
            $("#parentId").val(-1)
            $("#parentId").val(parentId)
            buttonDisplay("add");
        } else {
            parent.layer.msg("选中节点");
        }

    });

    // 删除
    $('#btn-del').on('click', function () {
        var menuId = $("#menuId").val();
        var name = $("#name").val();
        if (!menuId) {
            parent.layer.msg("请选择节点");
            return;
        }
        layer.confirm('真的删除' + name + '么?', function (index) {
            $.ajax({
                url: "/menu/remove/" + menuId,
                type: "post",
                success: function (r) {
                    if (r.code == 0) {
                        layer.msg(r.msg);
                        location.reload()
                    } else {
                        layer.msg(r.msg);
                    }
                },
                error: function (request) {
                    layer.alert("Connection error");
                },
            });
        });
    });


    // 编辑
    $('#btn-edit').on('click', function () {
        console.log("编辑节点")
        //输入框赋值
        var parentId = $("#menuId").val()
        if (parentId) {
            buttonDisplay("edit")
        } else {
            parent.layer.msg("选中节点");
        }
    });

    // 取消
    $('#btnCancel').on('click', function () {
        parent.layer.msg("取消");
        buttonDisplay("cancel");
    });


    // 查询
    $('#queryMenu').on('click', function () {
        parent.layer.msg("查询");
        treeLoad()
    });

    //监听新增提交
    form.on('submit(subAddButton)', function (data) {
        $.ajax({
            cache: true,
            type: "POST",
            dataType: 'json',
            url: "/menu/addForm",
            data: data.field,// 你的formid
            async: false, //异步
            success: function (data) {
                if (data.code == 0) {
                    parent.layer.msg("操作成功");
                    treeLoad();
                    buttonDisplay("cancel")
                } else {
                    parent.layer.alert(data.msg)
                }

            },
            error: function (request) {
                parent.layer.alert("Connection error");
            },
        });
        return false;
    });
    //监听编辑提交
    form.on('submit(subEditButton)', function (data) {
        parent.layer.msg("编辑");
        $.ajax({
            cache: true,
            type: "POST",
            dataType: 'json',
            url: "/menu/editForm",
            data: data.field,// 你的formid
            async: false, //异步
            success: function (data) {
                if (data.code == 0) {
                    parent.layer.msg("操作成功");
                    treeLoad();
                    buttonDisplay("cancel");
                } else {
                    parent.layer.alert(data.msg)
                }

            },
            error: function (request) {
                parent.layer.alert("Connection error");
            },
        });
        return false;
    });
});