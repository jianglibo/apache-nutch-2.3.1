package com.mymock.nutch.spark;

import java.util.List;

import org.apache.gora.spark.GoraSparkEngine;
import org.apache.gora.store.DataStore;
import org.apache.gora.store.DataStoreFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.nutch.storage.WebPage;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mymock.nutch.generated.DoubleZi;

import scala.Tuple2;

/**
 * http://spark.apache.org/docs/latest/programming-guide.html bin/gora
 * goracompiler gora-tutorial/src/main/avro/pageview.json
 * gora-tutorial/src/main/java/
 * 
 * @author jianglibo@gmail.com
 * 
 *         webpage--(flatmap)-->DoubleZi--(reduce)-->DoubleZi*count--
 * 
 *
 */
public class WordStatistics {
	private static final Logger log = LoggerFactory.getLogger(WordStatistics.class);

	private static final String USAGE = "LogAnalyticsSpark <input_data_store> <output_data_store>";

	/**
	 * map function used in calculation
	 */
	private static Function<WebPage, Iterable<Tuple2<String, Long>>> webPage2String = new Function<WebPage, Iterable<Tuple2<String, Long>>>() {
		@Override
		public List<Tuple2<String, Long>> call(WebPage wp) throws Exception {
			return null;
		}
	};

	/**
	 * reduce function used in calculation
	 */
	private static Function2<Long, Long, Long> redFunc = new Function2<Long, Long, Long>() {
		@Override
		public Long call(Long aLong, Long aLong2) throws Exception {
			return aLong + aLong2;
		}
	};

	/**
	 * metric function used after map phase
	 */
	private static PairFunction<String, Long, DoubleZi> metricFunc = new PairFunction<String, Long, DoubleZi>() {

		@Override
		public Tuple2<Long, DoubleZi> call(String dz) throws Exception {
			return null;
		}
	};

	public static void main(String[] args) throws Exception {
		WordStatistics logAnalyticsSpark = new WordStatistics();

		try {
			int ret = logAnalyticsSpark.run(args);
			System.exit(ret);
		} catch (Exception ex) {
			log.error("Error occurred!");
		}

	}

	public int run(String[] args) throws Exception {

		DataStore<String, WebPage> inStore;
		DataStore<String, DoubleZi> outStore;
		Configuration hadoopConf = new Configuration();

		inStore = DataStoreFactory.getDataStore(String.class, WebPage.class, hadoopConf);
		outStore = DataStoreFactory.getDataStore(String.class, DoubleZi.class, hadoopConf);

		// Spark engine initialization
		GoraSparkEngine<String, WebPage> goraSparkEngine = new GoraSparkEngine<>(String.class, WebPage.class);

		SparkConf sparkConf = new SparkConf().setAppName("Gora Spark Integration Application").setMaster("local");

		Class[] c = new Class[1];
		c[0] = inStore.getPersistentClass();
		sparkConf.registerKryoClasses(c);
		//
		JavaSparkContext sc = new JavaSparkContext(sparkConf);

		JavaPairRDD<String, WebPage> goraRDD = goraSparkEngine.initialize(sc, inStore);

		long count = goraRDD.count();
		log.info("Total Log Count: {}", count);

		JavaRDD<Tuple2<String, Long>> mappedGoraRdd = goraRDD.flatMapValues(webPage2String).values();

		JavaPairRDD<String, DoubleZi> reducedGoraRdd = JavaPairRDD.fromJavaRDD(mappedGoraRdd).reduceByKey(redFunc)
				.map(new Function<Tuple2<String, Long>, DoubleZi>() {
					@Override
					public DoubleZi call(Tuple2<String, Long> arg0) throws Exception {
						return null;
					}
				}).mapToPair(new PairFunction<DoubleZi, String, DoubleZi>() {
					@Override
					public Tuple2<String, DoubleZi> call(DoubleZi arg0) throws Exception {
						return null;
					}
				});// .mapToPair(metricFunc);

		log.info("MetricDatum count: {}", reducedGoraRdd.count());

		// Print output for debug purpose
		/*
		 * Map<String, MetricDatum> metricDatumMap =
		 * reducedGoraRdd.collectAsMap(); for (String key :
		 * metricDatumMap.keySet()) { System.out.println(key); }
		 */
		//

		// write output to datastore
		Configuration sparkHadoopConf = goraSparkEngine.generateOutputConf(outStore);
		reducedGoraRdd.saveAsNewAPIHadoopDataset(sparkHadoopConf);
		//

		inStore.close();
		outStore.close();

		log.info("Log completed with success");

		return 1;
	}
}
