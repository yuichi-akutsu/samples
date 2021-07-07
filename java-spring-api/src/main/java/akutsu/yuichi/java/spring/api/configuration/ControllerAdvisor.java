/*
 * MIT License
 *
 * Copyright (c) 2021 yuichi.akutsu
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package akutsu.yuichi.java.spring.api.configuration;

import akutsu.yuichi.java.spring.api.exception.SystemException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.apache.commons.lang3.StringUtils.defaultIfBlank;


/**
 * Controllerの例外ハンドリング
 * <p>
 * ControllerAdviceをクラスに付与し、
 * ExceptionHandler付与したメソッドを用意する事で
 * 全てのコントローラから委譲された例外のハンドリングを行っています。
 * </p>
 */
@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(SystemException.class)
    public ResponseEntity<String> systemException(SystemException systemException) {
        String message = defaultIfBlank(systemException.getMessage(), "system error");
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> systemException(Exception exception) {
        return new ResponseEntity<>("system error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
