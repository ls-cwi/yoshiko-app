package com.badlyby.yoshiko.internal.task;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.work.TaskFactory;
import org.cytoscape.work.TaskIterator;

import com.badlyby.yoshiko.internal.event.AnalysisCompletedListener;
import com.badlyby.yoshiko.internal.model.YoshikoAlgorithm;
import com.badlyby.yoshiko.internal.util.YoshikoUtil;

public class YoshikoAnalyzeTaskFactory implements TaskFactory {

	private final CyNetwork network;
	private final int analyze;
	private final int resultId;
	private final YoshikoAlgorithm alg;
	private final YoshikoUtil mcodeUtil;
	private final AnalysisCompletedListener listener;

	public YoshikoAnalyzeTaskFactory(final CyNetwork network,
								   final int analyze,
								   final int resultId,
								   final YoshikoAlgorithm alg,
								   final YoshikoUtil mcodeUtil,
								   final AnalysisCompletedListener listener) {
		this.network = network;
		this.analyze = analyze;
		this.resultId = resultId;
		this.alg = alg;
		this.mcodeUtil = mcodeUtil;
		this.listener = listener;
	}

	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(new YoshikoAnalyzeTask(network, analyze, resultId, alg, mcodeUtil, listener));
	}

	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return true;
	}
}
