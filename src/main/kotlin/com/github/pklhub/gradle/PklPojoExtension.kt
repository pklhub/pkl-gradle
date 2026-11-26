package com.github.pklhub.gradle

import com.github.pklhub.gradle.spec.JavaCodeGenSpec
import com.github.pklhub.gradle.spec.KotlinCodeGenSpec
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.model.ObjectFactory
import javax.inject.Inject

open class PklPojoExtension @Inject constructor(objects: ObjectFactory) {

    var javaCodeGenerators: NamedDomainObjectContainer<JavaCodeGenSpec> =
        objects.domainObjectContainer(JavaCodeGenSpec::class.java)

    var kotlinCodeGenerators: NamedDomainObjectContainer<KotlinCodeGenSpec> =
        objects.domainObjectContainer(KotlinCodeGenSpec::class.java)

    fun javaCodeGenerators(
        action: Action<NamedDomainObjectContainer<JavaCodeGenSpec>>,
    ) {
        action.execute(javaCodeGenerators)
    }

    fun kotlinCodeGenerators(
        action: Action<NamedDomainObjectContainer<KotlinCodeGenSpec>>,
    ) {
        action.execute(kotlinCodeGenerators)
    }
}
