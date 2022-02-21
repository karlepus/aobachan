package karlepus.aobachan.processor;

import karlepus.aobachan.api.annotation.AutoRegister;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashSet;
import java.util.Set;

public class AutoRegisterProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        var autoRegisterElements = roundEnv.getElementsAnnotatedWith(AutoRegister.class);
        for (var element : autoRegisterElements) {
            var enclosingElement = (TypeElement) element;
            for (var method : enclosingElement.getClass().getMethods()) {
                if ("register".equalsIgnoreCase(method.getName())) {
                    try {
                        method.invoke(null);
                    } catch (IllegalAccessException | InvocationTargetException ignored) {
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        var annotations = new LinkedHashSet<String>();
        annotations.add(AutoRegister.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }
}
