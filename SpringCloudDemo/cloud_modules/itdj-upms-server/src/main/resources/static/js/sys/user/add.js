layui.use(['form', 'layedit', 'laydate', 'element'], function () {
    var form = layui.form
        , layer = layui.layer
        , layedit = layui.layedit
        , laydate = layui.laydate
        , element = layui.element
        , $ = layui.jquery;

    //创建一个编辑器
    var editIndex = layedit.build('LAY_demo_editor');
    //自定义验证规则
    form.verify({
        username: function (value, item) { //value：表单的值、item：表单的DOM对象
            if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)) {
                return '用户名不能有特殊字符';
            }
            if (/(^\_)|(\__)|(\_+$)/.test(value)) {
                return '用户名首尾不能出现下划线\'_\'';
            }
            if (/^\d+\d+\d$/.test(value)) {
                return '用户名不能全为数字';
            }

        }
        , pass: [/(.+){6,12}$/, '密码必须6到12位']

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

    $("#deptTree").on("click", function () {
        console.log("部门菜单按钮")
    });

    /**
     * 获取树
     */
    function deptTree() {
        //页面层-自定义
        layer.open({
            type: 2,
            title: "部门树",
            closeBtn: false,//关闭按钮
            shift: 2,
            area: ['450px', '400px'],
            closeBtn: 1,//关闭按钮
            // btnAlign: 'c',
            content: "/dept/tree",
            success: function (layero, index) {

            },
            cancel: function (index, layero) {
                layer.close(index)

            }
        });
    }

});