package com.trifingzw.emodule.activity

import android.os.Build
import com.google.android.material.tabs.TabLayoutMediator
import com.trifingzw.emodule.R
import com.trifingzw.emodule.adapter.BtlFragmentStateAdapter
import com.trifingzw.emodule.databinding.ActivityProjectBinding
import com.trifingzw.emodule.fragment.BtlFragment
import com.trifingzw.emodule.helper.Project

class ProjectActivity : EModuleActivity() {
    private val binding by lazy {
        ActivityProjectBinding.inflate(
            layoutInflater
        )
    }
    private val tabMap by lazy {
        linkedMapOf(
            getString(R.string.conquest) to "conquest",
            getString(R.string.stage) to "stage",
            getString(R.string.event) to " event",
            getString(R.string.frontier) to " frontier",
            getString(R.string.generalstage) to "generalstage",
            getString(R.string.invadecorps) to "invadecorps",
            getString(R.string.warzone) to "warzone",
        )
    }
    private lateinit var project: Project
    private lateinit var fragments: Array<BtlFragment>

    override fun userInterface() {
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }

    override fun initialize() {
        project = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            intent.getParcelableExtra("project", Project::class.java)!!
        else
            intent.getParcelableExtra("project")!!

        binding.toolbar.subtitle = project.name

        fragments = tabMap.map { entry ->
            BtlFragment.newInstance(project.workPath, entry)
        }.toTypedArray()
        binding.viewPager.adapter = BtlFragmentStateAdapter(this, fragments)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = fragments[position].map.key
        }.attach()
    }
}