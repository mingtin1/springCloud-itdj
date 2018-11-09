layui.use(['form', 'layedit', 'laydate', 'element'], function () {
    var form = layui.form
        , layedit = layui.layedit
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
            url: "/user/addForm",
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


});