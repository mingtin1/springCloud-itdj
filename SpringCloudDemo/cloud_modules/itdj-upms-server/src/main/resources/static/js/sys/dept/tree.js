// layui方法
layui.use(['tree', 'table', 'vip_table', 'layer', 'form'], function () {

    // 操作对象
    var layer = layui.layer
        , $ = layui.jquery
        , form = layui.form;
    //初始化菜单树
    intoAjax()

    //初始化菜单树方法
    function intoAjax() {
        $.ajax({
            url: "/dept/tree",
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
                console.log(node)
                parent.setDept(node);
                var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                parent.layer.close(index);
            }
        });
    }

    $("#binReturn").on("click", function () {

        var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
        parent.layer.close(index);
    })
});