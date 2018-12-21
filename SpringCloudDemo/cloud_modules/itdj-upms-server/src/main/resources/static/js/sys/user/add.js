var dept;
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
        deptTree()
    });

    into();

    function into() {
        console.log("部门菜单按钮")
    }

    /**
     * 获取树
     */
    function deptTree() {
        //页面层-自定义
        layer.open({
            type: 2,
            closeBtn: false,//关闭按钮
            shift: 2,
            area: ['450px', '400px'],
            // closeBtn: 1,//关闭按钮
            // btnAlign: 'c',
            content: "/dept/deptTree",
            resize: false,
            Boolean: true,
            success: function (layero, index) {

            },
            cancel: function (index, layero) {
                layer.close(index)

            }
        });
    }

    dept = function deptId(data) {
        parent.layer.msg("部门赋值1");
        $("#deptTree").val(data.name);
        $("#deptId").val(data.id)
        roleBydeptId(data.id);
    }

    //获取角色列表
    function roleBydeptId(id) {
        $.ajax({
            url: "/role/roleList/" + id,
            type: "post",
            success: function (r) {

                parent.layer.msg("获取到数据");
                $("#roleCheckbox").html(roleHtml(r))
                form.render('checkbox'); //刷新checkbox复选框渲染
            },
            error: function (request) {
                layer.alert("Connection error");
            },
        });
    }

    function roleHtml(data) {
        var htmld = '';
        if (data.code == 0) {
            var obj = data.data;
            for (var i = 0; i < obj.length; i++) {
                var role = obj[i]
                console.log(role)
                htmld += '<input value=' + role.roleId + ' name="role[' + i + ']" title=' + role.roleName + ' type="checkbox">';
            }
        }
        return htmld;
    }
});

function setDept(data) {
    dept(data);
}