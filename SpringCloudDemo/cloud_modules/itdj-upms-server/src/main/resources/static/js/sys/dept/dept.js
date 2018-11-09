var editObj = null;
var ptable = null, tableId = 'deptTreeTable', layer = null;
var treeGrid = null;
var prefix = "/dept"
layui.config({
    base: '/frame/static/js/'   // 模块目录
}).extend({
    treeGrid: 'treeGrid'
}).use(['jquery', 'treeGrid', 'layer', 'vip_table'],
    function () {
        var layer = layui.layer
            , treeGrid = layui.treeGrid   //很重要
            , vipTable = layui.vip_table
            , $ = layui.$
            , active = {
            reload: function () {
                query();
            }, openAll: function () {
                openAll()
            }
        };
        var options = {
            id: tableId
            , elem: '#' + tableId
            , url: '/dept/listPage'
            , cellMinWidth: 100
            , idField: 'deptId'//必須字段
            , treeId: 'deptId'//树形id字段名称
            , treeUpId: 'parentId'//树形父id字段名称
            , treeShowName: 'name'//以树形式显示的字段
            , heightRemove: [".dHead", 10]//不计算的高度,表格设定的是固定高度，此项不生效
            , height: vipTable.getFullHeight()
            , isFilter: false
            , iconOpen: false//是否显示图标【默认显示】
            , isOpenDefault: false//节点默认是展开还是折叠【默认展开】
            , loading: false
            , method: 'post'
            , isPage: false
            , cols: [[
                {field: 'deptId', width: '10%', align: 'center', title: '编码'}
                , {field: 'name', width: '40%', title: '部门名称'}
                , {field: 'orderNum', width: '10%', align: 'center', title: "排序"}
                , {field: 'delFlag', title: '状态', align: 'center', width: '10%', sort: true, templet: '#delFlag'}
                , {width: '20%', title: '操作', align: 'center', toolbar: '#barOption'}
            ]]
            , parseData: function (res) {//数据加载后回调
                return res;
            }
        };

        ptable = treeGrid.render(options);
        //监听事件
        treeGrid.on('tool(' + tableId + ')', function (obj) {
            if (obj.event === 'del') {//删除行
                var data = obj.data;
                layer.confirm('真的删除行么', function (index) {
                    $.ajax({
                        url: "/dept/remove/"+data.deptId,
                        type: "post",
                        success: function (r) {
                            if (r.code == 0) {
                                layer.msg(r.msg);
                                query();
                            } else {
                                layer.msg(r.msg);
                            }
                        },
                        error: function (request) {
                            layer.alert("Connection error");
                        },
                    });
                });
            } else if (obj.event === "add") {//添加行
                add(obj.data);
            } else if (obj.event === "editBtn") {//编辑
                editOpen(obj.data.deptId);
            }
        });
        //// 刷新
        $('#btn-refresh').on('click', function () {
            query()
        });

        //父级新增
        $('#btn-add').on('click', function () {
            addOpen(0);
        });

        //折叠/展开
        $('#btn-openAll').on('click', function () {
            openAll()
        });

        //点击事务绑定
        $('.my-btn-box .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

        //展开还是折叠
        function openAll() {
            var treedata = treeGrid.getDataTreeList(tableId);
            treeGrid.treeOpenAll(tableId, !treedata[0][treeGrid.config.cols.isOpen]);
        }

        //重载
        function query() {
            var deptName = $('#deptName');
            treeGrid.query(tableId, {
                where: {
                    name: deptName.val()
                }
            });
        }
    });


//添加
function add(parentObj) {
    var parentId = parentObj ? parentObj.deptId : 0;
    addOpen(parentId)
}

function addOpen(parentId) {
    layer.open({
        type: 2,
        title: "新增",
        closeBtn: false,//关闭按钮
        shift: 2,
        area: ['400px', '300px'],
        //btn: ['新增', '取消'],
        closeBtn: 1,//关闭按钮
        // btnAlign: 'c',
        //maxmin : true,//最大最小化
        content: prefix + "/add?" + "parentId=" + parentId,
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
function editOpen(id) {
    //页面层-自定义
    layer.open({
        type: 2,
        title: "编辑",
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

//重载
function queryBtn() {
    $('#btn-refresh').trigger("click");
}