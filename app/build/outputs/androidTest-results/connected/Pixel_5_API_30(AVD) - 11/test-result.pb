"º>·>
ç'
com.google.testing.platformÒPLUGIN_ERROR"TEST*xException thrown during onBeforeAll invocation of plugin com.google.testing.platform.plugin.android.AndroidDevicePlugin.:´&com.google.testing.platform.api.plugin.PluginException: Exception thrown during onBeforeAll invocation of plugin com.google.testing.platform.plugin.android.AndroidDevicePlugin.
	at com.google.testing.platform.plugin.PluginLifecycleKt.invokeOrThrow(PluginLifecycle.kt:376)
	at com.google.testing.platform.plugin.PluginLifecycleKt.invokeOrThrow$default(PluginLifecycle.kt:367)
	at com.google.testing.platform.plugin.PluginLifecycle$onBeforeAll$1.invoke(PluginLifecycle.kt:162)
	at com.google.testing.platform.plugin.PluginLifecycle$onBeforeAll$1.invoke(PluginLifecycle.kt:156)
	at com.google.testing.platform.core.telemetry.common.noop.NoopDiagnosticsScope.recordEvent(NoopDiagnosticsScope.kt:35)
	at com.google.testing.platform.core.telemetry.TelemetryKt.recordEvent(Telemetry.kt:105)
	at com.google.testing.platform.core.telemetry.TelemetryKt.recordEvent$default(Telemetry.kt:98)
	at com.google.testing.platform.plugin.PluginLifecycle.onBeforeAll(PluginLifecycle.kt:156)
	at com.google.testing.platform.executor.SingleDeviceExecutor$execute$4.invoke(SingleDeviceExecutor.kt:104)
	at com.google.testing.platform.executor.SingleDeviceExecutor$execute$4.invoke(SingleDeviceExecutor.kt:104)
	at com.google.testing.platform.lib.cancellation.ProcessCancellationContext.runUnlessCancelled(ProcessCancellationContext.kt:130)
	at com.google.testing.platform.executor.SingleDeviceExecutor.execute(SingleDeviceExecutor.kt:104)
	at com.google.testing.platform.RunnerImpl.run(RunnerImpl.kt:117)
	at com.google.testing.platform.server.strategy.NonInteractiveServerStrategy$run$5.invoke(NonInteractiveServerStrategy.kt:95)
	at com.google.testing.platform.server.strategy.NonInteractiveServerStrategy$run$5.invoke(NonInteractiveServerStrategy.kt:94)
	at com.google.testing.platform.core.telemetry.common.noop.NoopDiagnosticsScope.recordEvent(NoopDiagnosticsScope.kt:35)
	at com.google.testing.platform.core.telemetry.TelemetryKt.recordEvent(Telemetry.kt:66)
	at com.google.testing.platform.server.strategy.NonInteractiveServerStrategy.run(NonInteractiveServerStrategy.kt:94)
	at com.google.testing.platform.main.MainKt$main$4.invokeSuspend(Main.kt:75)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:106)
	at kotlinx.coroutines.EventLoopImplBase.processNextEvent(EventLoop.common.kt:279)
	at kotlinx.coroutines.BlockingCoroutine.joinBlocking(Builders.kt:85)
	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking(Builders.kt:59)
	at kotlinx.coroutines.BuildersKt.runBlocking(Unknown Source)
	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking$default(Builders.kt:38)
	at kotlinx.coroutines.BuildersKt.runBlocking$default(Unknown Source)
	at com.google.testing.platform.main.MainKt.main(Main.kt:73)
	at com.google.testing.platform.main.MainKt.main$default(Main.kt:35)
	at com.google.testing.platform.main.MainKt.main(Main.kt)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:568)
	at com.google.testing.platform.launcher.Launcher.main(Launcher.java:149)
Caused by: com.google.testing.platform.core.error.UtpException: ErrorName: UNKNOWN
NameSpace: DdmlibAndroidDeviceController
ErrorCode: 1
ErrorType: TEST
Message: Failed to install APK(s): /home/marcos/MEGA/PROYECTOS-NO-TOCAR-NI-MOVER/MiAforoBus/app/build/intermediates/apk/androidTest/debug/app-debug-androidTest.apk
	at com.android.tools.utp.plugins.deviceprovider.ddmlib.DdmlibAndroidDeviceController$executeAsync$deferred$1.invokeSuspend(DdmlibAndroidDeviceController.kt:223)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:106)
	at kotlinx.coroutines.scheduling.CoroutineScheduler.runSafely(CoroutineScheduler.kt:571)
	at kotlinx.coroutines.scheduling.CoroutineScheduler$Worker.executeTask(CoroutineScheduler.kt:750)
	at kotlinx.coroutines.scheduling.CoroutineScheduler$Worker.runWorker(CoroutineScheduler.kt:678)
	at kotlinx.coroutines.scheduling.CoroutineScheduler$Worker.run(CoroutineScheduler.kt:665)
Caused by: com.android.ddmlib.InstallException: Unknown failure: cmd: Can't find service: package
	at com.android.ddmlib.internal.DeviceImpl.installRemotePackage(DeviceImpl.java:1376)
	at com.android.ddmlib.internal.DeviceImpl.installPackage(DeviceImpl.java:1202)
	at com.android.tools.utp.plugins.deviceprovider.ddmlib.DdmlibAndroidDevice.installPackage(DdmlibAndroidDevice.kt)
	at com.android.tools.utp.plugins.deviceprovider.ddmlib.DdmlibAndroidDeviceController$executeAsync$deferred$1.invokeSuspend(DdmlibAndroidDeviceController.kt:192)
	... 6 more
Ê
ä
DdmlibAndroidDeviceControllerUNKNOWN"TEST*šFailed to install APK(s): /home/marcos/MEGA/PROYECTOS-NO-TOCAR-NI-MOVER/MiAforoBus/app/build/intermediates/apk/androidTest/debug/app-debug-androidTest.apk:’com.google.testing.platform.core.error.UtpException: ErrorName: UNKNOWN
NameSpace: DdmlibAndroidDeviceController
ErrorCode: 1
ErrorType: TEST
Message: Failed to install APK(s): /home/marcos/MEGA/PROYECTOS-NO-TOCAR-NI-MOVER/MiAforoBus/app/build/intermediates/apk/androidTest/debug/app-debug-androidTest.apk
	at com.android.tools.utp.plugins.deviceprovider.ddmlib.DdmlibAndroidDeviceController$executeAsync$deferred$1.invokeSuspend(DdmlibAndroidDeviceController.kt:223)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:106)
	at kotlinx.coroutines.scheduling.CoroutineScheduler.runSafely(CoroutineScheduler.kt:571)
	at kotlinx.coroutines.scheduling.CoroutineScheduler$Worker.executeTask(CoroutineScheduler.kt:750)
	at kotlinx.coroutines.scheduling.CoroutineScheduler$Worker.runWorker(CoroutineScheduler.kt:678)
	at kotlinx.coroutines.scheduling.CoroutineScheduler$Worker.run(CoroutineScheduler.kt:665)
Caused by: com.android.ddmlib.InstallException: Unknown failure: cmd: Can't find service: package
	at com.android.ddmlib.internal.DeviceImpl.installRemotePackage(DeviceImpl.java:1376)
	at com.android.ddmlib.internal.DeviceImpl.installPackage(DeviceImpl.java:1202)
	at com.android.tools.utp.plugins.deviceprovider.ddmlib.DdmlibAndroidDevice.installPackage(DdmlibAndroidDevice.kt)
	at com.android.tools.utp.plugins.deviceprovider.ddmlib.DdmlibAndroidDeviceController$executeAsync$deferred$1.invokeSuspend(DdmlibAndroidDeviceController.kt:192)
	... 6 more
à
Ý*1Unknown failure: cmd: Can't find service: package:§com.android.ddmlib.InstallException: Unknown failure: cmd: Can't find service: package
	at com.android.ddmlib.internal.DeviceImpl.installRemotePackage(DeviceImpl.java:1376)
	at com.android.ddmlib.internal.DeviceImpl.installPackage(DeviceImpl.java:1202)
	at com.android.tools.utp.plugins.deviceprovider.ddmlib.DdmlibAndroidDevice.installPackage(DdmlibAndroidDevice.kt)
	at com.android.tools.utp.plugins.deviceprovider.ddmlib.DdmlibAndroidDeviceController$executeAsync$deferred$1.invokeSuspend(DdmlibAndroidDeviceController.kt:192)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:106)
	at kotlinx.coroutines.scheduling.CoroutineScheduler.runSafely(CoroutineScheduler.kt:571)
	at kotlinx.coroutines.scheduling.CoroutineScheduler$Worker.executeTask(CoroutineScheduler.kt:750)
	at kotlinx.coroutines.scheduling.CoroutineScheduler$Worker.runWorker(CoroutineScheduler.kt:678)
	at kotlinx.coroutines.scheduling.CoroutineScheduler$Worker.run(CoroutineScheduler.kt:665)
