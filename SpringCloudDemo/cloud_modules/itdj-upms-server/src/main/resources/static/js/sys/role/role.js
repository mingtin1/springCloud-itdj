// layui方法
var prefix = "/role"
var tableIns
layui.use(['table', 'form', 'layer', 'vip_table'], function () {
        // 操作对象
        var layer = layui.layer
            , table = layui.table
            , vipTable = layui.vip_table
            , $ = layui.$
            , active = {
            reload: function () {
                // var username = $('#username');
                //执行重载
                tableIns.reload({
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                    // , where: {
                    //     username: username.val()
                    // }
                });
            },
            add: function () {
                add();
            }
        };

        // 表格渲染
        tableIns = table.render({
            elem: '#dateTable'                  //指定原始表格元素选择器（推荐id选择器）
            , title: '数据表'
            , height: vipTable.getFullHeight()    //容器高度
            , cols: [[                  //标题栏
                {
                    field: 'roleId',
                    title: 'ID',
                    align: 'center',
                    width: 80,
                    sort: true,
                    fixed: 'left',
                }
                , {field: 'roleName', title: '角色名称', align: 'center', width: 80}
                , {field: 'roleCode', title: '角色标识', align: 'center', width: 160}
                , {field: 'roleDesc', title: '角色描述', align: 'center', width: 160}
                , {field: 'deptName', title: '所属部门', align: 'center', width: 160}
                , {field: 'createTime', title: '创建时间', align: 'center', width: 250, sort: true}
                , {fixed: 'right', title: '操作', width: "200", align: 'center', toolbar: '#barOption'} //这里的toolbar值是模板元素的选择器
            ]]
            , id: 'roleDateTable'
            , url: '/role/listPage/' //数据接口
            , page: true
            , limit: 10
            , limits: [10, 20, 50]
            , done: function (res, curr, count) {
            }
        });


        //监听行工具事件
        table.on('tool(dateTable)', function (obj) {
            var data = obj.data;
            if (obj.event === 'del') {
                del(data);
            }
            else if (obj.event === 'edit') {
                editUser(data.userId)
            }
        });


        $('.my-btn-box .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

        function del(id) {
            layer.confirm('真的删除行么', function (index) {
                $.ajax({
                    url: "/role/remove",
                    type: "post",
                    data: {
                        'id': id
                    },
                    success: function (r) {
                        if (r.code == 0) {
                            layer.msg(r.msg);
                            reLoad();
                        } else {
                            layer.msg(r.msg);
                        }
                    },
                    error: function (request) {
                        layer.alert("Connection error");
                    },
                });
            });
        }
    }
);

/**
 * 新增
 */
function add() {
    //页面层-自定义
    layer.open({
        type: 2,
        title: "新增用户",
        closeBtn: false,//关闭按钮
        shift: 2,
        area: ['400px', '300px'],
        //btn: ['新增', '取消'],
        closeBtn: 1,//关闭按钮
        // btnAlign: 'c',
        //maxmin : true,//最大最小化
        content: prefix + "/add",
        success: function (layero, index) {

        },
        cancel: function (index, layero) {
            if (confirm('确定要关闭么')) { //只有当点击confirm框的确定时，该层才会关闭
                layer.close(index)
            }
            return false;
        }
    });
}

/**
 * 编辑用户
 * @param id
 */
function editUser(id) {
    //页面层-自定义
    layer.open({
        type: 2,
        title: "编辑用户",
        closeBtn: false,//关闭按钮
        shift: 2,
        area: ['400px', '300px'],
        closeBtn: 1,//关闭按钮
        // btnAlign: 'c',
        //maxmin : true,//最大最小化
        content: prefix + "/edit/" + id,
        success: function (layero, index) {

        },
        cancel: function (index, layero) {
            layer.close(index)
            return false;
        }
    });
}

/**
 * 刷新
 */
function reLoad() {
    tableIns.reload();
}