layui.use(['form', 'layedit', 'laydate', 'tree', 'element'], function () {
    var form = layui.form
        , layedit = layui.layedit
        , tree = layui.tree
        , $ = layui.jquery;

    //创建一个编辑器
    var editIndex = layedit.build('LAY_demo_editor');
    //自定义验证规则
    form.verify({
        roleName: function (value, item) { //value：表单的值、item：表单的DOM对象
            if (value.length > 20 || value.length < 3) {
                return '长度在 3 到 20 个字符';
            }
        }
        , roleCode: function (value, item) { //value：表单的值、item：表单的DOM对象
            if (value.length > 20 || value.length < 3) {
                return '长度在 3 到 20 个字符';
            }
        }
        , roleDesc: function (value, item) { //value：表单的值、item：表单的DOM对象
            if (value.length > 20 || value.length < 3) {
                return '长度在 3 到 20 个字符';
            }
        }
        , content: function (value) {
            layedit.sync(editIndex);
        }
    });
    //监听提交
    form.on('submit(subButton)', function (data) {
        $.ajax({
            cache: true,
            dataType: 'json',
            type: "POST",
            url: "/role/roleUpdate",
            data: data.field,// 你的formid
            async: false, //异步
            success: function (data) {
                if (data.code == 0) {
                    parent.layer.msg("操作成功");
                    parent.reLoad();
                    var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                    parent.layer.close(index);
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


    /**
     * 下拉树初始化
     * @param data
     */
    function infoTree(data) {
        tree({
            elem: "#classtree",
            nodes: data,
            click: function (node) {
                var $select = $($(this)[0].elem).parents(".layui-form-select");
                $select.removeClass("layui-form-selected").find(".layui-select-title span")
                    .html(node.name).end().find("input:hidden[name='roleDeptId']").val(node.id);
            }
        });
    }


    /**
     * 点击隐藏
     */
    $(".downpanel").on("click", ".layui-select-title", function (e) {
        $(".layui-form-select").not($(this).parents(".layui-form-select"))
            .removeClass("layui-form-selected");
        $(this).parents(".downpanel").toggleClass("layui-form-selected");
        layui.stope(e);
    }).on("click", "dl i", function (e) {
        layui.stope(e);
    });
    $(".downpanel").on("click", function (e) {
        $(".layui-form-select").removeClass("layui-form-selected");
    });


    $.ajax({
        cache: true,
        dataType: 'json',
        type: "POST",
        url: "/dept/tree",
        async: false, //异步
        success: function (data) {
            infoTree(data)
        },
        error: function (request) {
            parent.layer.alert("Connection error");
        },
    });
});