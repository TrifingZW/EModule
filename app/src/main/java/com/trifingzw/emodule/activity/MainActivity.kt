package com.trifingzw.emodule.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.permissionx.guolindev.PermissionX
import com.trifingzw.emodule.R
import com.trifingzw.emodule.adapter.ProjectAdapter
import com.trifingzw.emodule.adapter.SpacesItemDecoration
import com.trifingzw.emodule.databinding.ActivityMainBinding
import com.trifingzw.emodule.helper.Project
import com.trifingzw.emodule.helper.treeToPath
import java.io.File


class MainActivity : EModuleActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(
            layoutInflater
        )
    }
    private lateinit var adapter: ProjectAdapter

    private lateinit var newProjectDialog: AlertDialog

    override fun userInterface() {
        val recyclerView = binding.appBarMain.contentMain.recyclerView

        //设置View
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)


        binding.appBarMain.fab.setOnClickListener { _ -> newProjectDialog.show() }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val toggle = ActionBarDrawerToggle(this, drawerLayout, binding.appBarMain.toolbar, R.string.drawer_open, R.string.drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_create -> newProjectDialog.show()
            }
            return@setNavigationItemSelectedListener true
        }


        // 设置LayoutManager
        recyclerView.setLayoutManager(LinearLayoutManager(this))
        recyclerView.addItemDecoration(SpacesItemDecoration(32))

        // 设置Adapter
        adapter = ProjectAdapter { startActivity(Intent(this, ProjectActivity::class.java).putExtra("project", it)) }
        recyclerView.setAdapter(adapter)
    }


    override fun initialize() {
        permission()
        createNewProjectDialog()
    }

    @SuppressLint("InflateParams")
    private fun createNewProjectDialog() {
        val view = layoutInflater.inflate(R.layout.new_project_dialog, null)
        val name = view.findViewById<TextInputEditText>(R.id.name_edit)
        val description = view.findViewById<TextInputEditText>(R.id.description_edit)
        val workPath = view.findViewById<TextInputEditText>(R.id.work_path_edit)
        val layout = view.findViewById<TextInputLayout>(R.id.work_path_layout)

        val getWorkPath = registerForActivityResult(ActivityResultContracts.OpenDocumentTree()) { uri: Uri? ->
            uri?.let {
                workPath?.setText(treeToPath(it))
            }
        }

        newProjectDialog = MaterialAlertDialogBuilder(this)
            .setView(view)
            .setTitle("创建项目")
            .setPositiveButton("确定") { dialog, _ ->
                if (File(workPath.text.toString()).isDirectory) {
                    dialog.dismiss()
                    val project = Project(name.text.toString(), description.text.toString(), workPath.text.toString())
                    adapter.add(project)
                    Toast.makeText(this, "创建成功", Toast.LENGTH_LONG).show()
                } else Toast.makeText(this, "工作目录输入错误！", Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("取消") { dialog, _ -> dialog.dismiss() }
            .create()

        layout?.setEndIconOnClickListener {
            getWorkPath.launch(null)
        }
    }

    private fun permission() {
        val requestList = ArrayList<String>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            requestList.add(Manifest.permission.MANAGE_EXTERNAL_STORAGE)
        else {
            requestList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            requestList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        PermissionX.init(this)
            .permissions(requestList)
            .onExplainRequestReason { scope, deniedList ->
                val message = "EModule需要以下权限"
                scope.showRequestReasonDialog(deniedList, message, "ALLOW 允许", "DENY 拒绝")
            }.request { allGranted, _, _ -> if (!allGranted) finish() }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_exit -> finish()
        }
        return true
    }
}