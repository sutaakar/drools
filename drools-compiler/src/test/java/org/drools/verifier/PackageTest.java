package org.drools.verifier;

import static org.junit.Assert.*;

import org.drools.compiler.test.Man;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;

public class PackageTest {

    @Test
    public void testDRL() {
        String drl =
                "import " + Man.class.getCanonicalName() + ";\n" +
                "\n" +
                "global java.util.List list;" +
                "\n" +
                "rule \"To be or not to be\"\n" +
                "when\n" +
                "    aaa: String ( true )\n" +
                "    $m : Man( \"john\" , 18 , $w ; )\n" +
                "then\n" +
                "    list.add($w); " +
                "end";

        KieFileSystem kfs = KieServices.Factory.get().newKieFileSystem().write( "src/main/resources/r1.drl", drl );

        KieBuilder kbuilder = KieServices.Factory.get().newKieBuilder(kfs);

        kbuilder.buildAll();

        KieBase kbase = KieServices.Factory.get()
                .newKieContainer(kbuilder.getKieModule().getReleaseId())
                .getKieBase();

        assertEquals(2, kbase.getKiePackages().size());
    }
}
