package utils

import (
	"fmt"
	"path/filepath"
	"regexp"
	"strconv"
	"strings"
)

/**
 * Formate the Error Mesage, Compile Time + Run Time Error Messages
 * Replace the uncessary path of the src code file with "<filename>.<extension>"
 *
 * Adjust the original line number where error happens to follow the user code number line
 * e.g, if original error line number is 8 and the user code start from line 4 in the main file
 * then the user should see error line number as  (8 - 4 + 1 = 5)
 *
 * errMsg 	 - Error message generated while compiling or runing code
 * startLine - line number from where user code begins
 * language	 - the corresponding language of the error message
 */
func ReformatError(errMsg string, startLine int, language string) string {
	switch strings.ToLower(language) {
		case "java": return formatJavaError(errMsg, startLine);
		case "cpp": return formatCppError(errMsg, startLine)
		case "python": return formatPythonError(errMsg, startLine)
		default: return errMsg
	}
}

/**
 * Java Specific Error Message Formatting
 */
func formatJavaError(stdErr string, startLine int) string {
 	re := regexp.MustCompile(`Main\.java:(\d+)`)
	stdErr = strings.ReplaceAll(stdErr, "/tmp/Main.java", "Main.java");
	return re.ReplaceAllStringFunc(stdErr, func(match string) string {
		submatch := re.FindStringSubmatch(match)
		if len(submatch) < 2 {
			return match
		}
		lineNum, _ := strconv.Atoi(submatch[1])
		newLine := lineNum  - startLine + 1;
		return fmt.Sprintf("Main.java:%d", newLine)
	})
}

/**
 * C++ Specific Error Message Formatting
 */
func formatCppError(stdErr string, startLine int) string {
	stdErr = strings.ReplaceAll(stdErr, "/tmp/main.cpp", "main.cpp");
	lines := strings.Split(stdErr, "\n")

	// Regex to match "main.cpp:<line>:<col>:"
	reHeader := regexp.MustCompile(`^main\.cpp:(\d+):(\d+):`)
	// Regex to match "<spaces><line> |"
	reSnippet := regexp.MustCompile(`^(\s*)(\d+)(\s*\|)(.*)`)

	for i, line := range lines {
		if reHeader.MatchString(line) {
			matches := reHeader.FindStringSubmatch(line)
			oldLine, err := strconv.Atoi(matches[1])
			if err != nil { continue }
			newLine := oldLine - startLine + 1
			lines[i] = reHeader.ReplaceAllString(line, fmt.Sprintf("main.cpp:%d:%s:", newLine, matches[2]))
		}else if reSnippet.MatchString(line) {
			matches := reSnippet.FindStringSubmatch(line)
			oldNum, err := strconv.Atoi(matches[2])
			if err != nil { continue }
			newNum := oldNum - startLine + 1
			width := len(matches[2])
			lines[i] = fmt.Sprintf("%s%*d%s%s", matches[1], width, newNum,  matches[3],  matches[4])
		}
	}
	return strings.Join(lines, "\n")
}

/**
 * Python Specific Error Message Formatting
 */
func formatPythonError(stdErr string, startLine int) string {
	lines := strings.Split(stdErr, "\n")

	reFileLine := regexp.MustCompile(`^(.*File\s+")(.*)(".*line\s+)(\d+)(.*)$`)
	reMsgLine := regexp.MustCompile(`line\s+(\d+)`)

	for i, line := range lines {
		if reFileLine.MatchString(line) {
			matches := reFileLine.FindStringSubmatch(line)
			prefix := matches[1]          
			fullPath := matches[2]        
			middle := matches[3]         
			oldLine, _ := strconv.Atoi(matches[4])
			suffix := matches[5]

			filename := filepath.Base(fullPath)        
			newLine := oldLine - startLine + 1        

			lines[i] = fmt.Sprintf("%s%s%s%d%s", prefix, filename, middle, newLine, suffix)
		}else {
			lines[i] = reMsgLine.ReplaceAllStringFunc(lines[i], func(match string) string {
				numRe := regexp.MustCompile(`\d+`)
				numStr := numRe.FindString(match)
				num, _ := strconv.Atoi(numStr)
				newNum := num - startLine + 1
				return strings.Replace(match, numStr, strconv.Itoa(newNum), 1)
			})
		}
	}
	
	return strings.Join(lines, "\n")
}