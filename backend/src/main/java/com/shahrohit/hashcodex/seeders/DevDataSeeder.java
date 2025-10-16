package com.shahrohit.hashcodex.seeders;

import com.shahrohit.hashcodex.adapters.ProblemAdapter;
import com.shahrohit.hashcodex.entities.*;
import com.shahrohit.hashcodex.enums.Language;
import com.shahrohit.hashcodex.enums.ProblemDifficulty;
import com.shahrohit.hashcodex.enums.Role;
import com.shahrohit.hashcodex.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class DevDataSeeder implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;
    private final ProblemRepository problemRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final ProblemTopicRepository problemTopicRepository;
    private final ProblemTestcaseRepository problemTestcaseRepository;
    private final ProblemCodeRepository problemCodeRepository;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) seedUser();
        if (problemRepository.count() == 0) seedProblem();
    }

    public void seedUser() {
        User user = new User();
        user.setPublicId(UUID.randomUUID());
        user.setEmail("hikameb683@lorkex.com");
        user.setHashedPassword(passwordEncoder.encode("admin@123"));
        user.setName("Hashcodex Admin");
        user.setEmailVerified(true);
        user.setRole(Role.ADMIN);
        user.setEnabled(true);
        userRepository.save(user);
    }

    public void seedProblem() {
        Topic array = new Topic(null, "array", "Array");
        Topic linkedList = new Topic(null, "linked-list", "Linked List");
        Topic stack = new Topic(null, "stack", "Stack");
        Topic queue = new Topic(null, "queue", "Queue");
        Topic hashTable = new Topic(null, "hash-table", "Hash Table");
//
        List<Topic> topics = topicRepository.saveAll(List.of(array, linkedList, stack, queue, hashTable));

        Problem problem = new Problem();
        problem.setNumber(1);
        problem.setTitle("Two Sum");
        problem.setSlug("two-sum");
        problem.setDifficulty(ProblemDifficulty.EASY);
        problem.setParams("nums\ntarget");
        problem.setActive(true);
        problem.setTimeLimit(0.5);
        problem.setDescription("""
            Given an array of integers `nums` and an integer `target`, return indices of the two numbers such that they add up to `target`.
            
            You may assume that each input would have **exactly one solution**, and you may not use the same element twice.
            
            You can return the answer in any order.
            
            **Example 1:**
            ```
            Input: nums = [2,7,11,15], target = 9
            Output:  [0,1]
            Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].
            ```
            
            Example 2:
            ```
            Input: nums = [3,2,4], target = 6
            Output: [1,2]
            ```
            Example 3:
            ```
            Input: nums = [3,3], target = 6
            Output: [0,1]
            ```
            
            Constraints:
            
            - `2 <= nums.length <= 104`
            - `109 <= nums[i] <= 109`
            - `109 <= target <= 109`
            - `Only one valid answer exists.`
            
            
            Follow-up: Can you come up with an algorithm that is less than  $O(n^2)$ time complexity?
            """);

        problem = problemRepository.save(problem);

        // topics
        ProblemTopic pt1 = ProblemAdapter.toEntity(problem, topics.getFirst());
        ProblemTopic pt2 = ProblemAdapter.toEntity(problem, topics.getLast());
        problemTopicRepository.saveAll(List.of(pt1, pt2));


        // code
        problemCodeRepository.saveAll(List.of(seedJavaCode(problem), seedCPPCode(problem), seedPythonCode(problem)));

        // testcases
        ProblemTestcase t1 = new ProblemTestcase(null, problem, true, "2 7 11 15\n9", "0 1");
        ProblemTestcase t2 = new ProblemTestcase(null, problem, true, "3 2 4\n6", "1 2");
        ProblemTestcase t3 = new ProblemTestcase(null, problem, true, "3 3\n6", "0 1");
        ProblemTestcase t4 = new ProblemTestcase(null, problem, false, "5 2 3 4 3 2 7\n4", "1 5");
        ProblemTestcase t5 = new ProblemTestcase(null, problem, false, "4 1 2 0 5 9 8 3 11 10\n17", "5 6");
        ProblemTestcase t6 = new ProblemTestcase(null, problem, false, "1 1 1 1 1 2 1 1 1 1 1 1 1 1 1 1 2 1\n4", "5 16");

        problemTestcaseRepository.saveAll(List.of(t1, t2, t3, t4, t5, t6));

    }

    private ProblemCode seedJavaCode(Problem problem){
        ProblemCode code = new ProblemCode();
        code.setProblem(problem);
        code.setLanguage(Language.JAVA);
        code.setDriverCode("""
            import java.io.*;
            import java.util.*;
            
            {{code}}
            
            public class Main {
                public static void main(String args[]) {
                    BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
                    try {
                        String input1 = bf.readLine().trim();
                        if(input1.isEmpty())throw new Exception("nums should contains atleast 2 elements");
                        String[] parts = input1.split("\\\\s+");
                        if(parts.length < 2) throw new Exception("nums should contains atleast 2 elements");
                        int[] nums = new int[parts.length];
                        for(int i = 0; i < nums.length; i++) nums[i] = Integer.parseInt(parts[i]);
            
                        String input2 = bf.readLine().trim();
                        if(input2.isEmpty()) throw new Exception("Target value cannot be empty");
                        int target = Integer.parseInt(input2);
            
                        PrintStream originalOut = System.out;
                        PrintStream originalErr = System.err;
            
                        System.setOut(new PrintStream(OutputStream.nullOutputStream()));
                        System.setErr(new PrintStream(OutputStream.nullOutputStream()));
            
                        try {
                            Solution sol = new Solution();
                            int[] results = sol.twoSum(nums, target);
                            System.setOut(originalOut);
                            System.setErr(originalErr);
            
                            if(results.length == 0) return;
                            if(results.length == 2) {
                                System.out.println(Math.min(results[0], results[1]) + " " + Math.max(results[0], results[1]));
                                return;
                            }
            
                            System.out.println(results[1]);
                            for(int i = 1; i < results.length; i++){
                                System.out.print(" " + results[i]);
                            }
            
                        }catch (Exception e) {
                            System.setOut(originalOut);
                            System.setErr(originalErr);
                            StackTraceElement[] trace = Arrays.stream(e.getStackTrace())
                            .filter(el -> !el.getClassName().equals("Main"))
                            .toArray(StackTraceElement[]::new);
                            e.setStackTrace(trace);
                            e.printStackTrace();
                            System.exit(1);
                        }
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                        System.exit(2);
                    }
                }
            }
            """);
        code.setUserCode("""
            class Solution {
                public int[] twoSum(int[] nums, int target){
            
                }
            
            }
            """);

        code.setSolutionCode("""
            class Solution {
                public int[] twoSum(int[] nums, int target){
                    Map<Integer, Integer> map = new HashMap<>();
                    for (int i = 0; i < nums.length; i++) {
                        int complement = target - nums[i];
                        if (map.containsKey(complement)) {
                            return new int[] { map.get(complement), i };
                        }
                        map.put(nums[i], i);
                    }
                    return new int[] {};
                }
            
            }
            """);

        return code;
    }

    private ProblemCode seedCPPCode(Problem problem){
        ProblemCode code = new ProblemCode();
        code.setProblem(problem);
        code.setLanguage(Language.CPP);
        code.setDriverCode("""
            #include <bits/stdc++.h>
            using namespace std;
            
            {{code}}
            
            int main() {
                ios::sync_with_stdio(false);
                cin.tie(nullptr);
            
                try {
                    string input1;
                    if (!getline(cin, input1)) throw runtime_error("nums should contains atleast 2 elements");
                    stringstream ss(input1);
                    vector<int> nums;
                    string token;
            
                    while (ss >> token) nums.push_back(stoi(token));
                    if (nums.size() < 2) throw runtime_error("nums should contains atleast 2 elements");
            
                    string input2;
                    if (!getline(cin, input2) || input2.empty())
                        throw runtime_error("Target value cannot be empty");
                    int target = stoi(input2);
            
                    streambuf* coutBuf = cout.rdbuf();
                    streambuf* cerrBuf = cerr.rdbuf();
                    ofstream nullStream("/dev/null");
                    cout.rdbuf(nullStream.rdbuf());
                    cerr.rdbuf(nullStream.rdbuf());
                    try {
                        Solution sol;
                        vector<int> result = sol.twoSum(nums, target);
                        cout.rdbuf(coutBuf);
                        cerr.rdbuf(cerrBuf);
            
                        if (result.size() == 0) return 0;
                        if (result.size() == 2) {
                            cout << min(result[0], result[1]) << " " << max(result[0], result[1]) << endl;
                            return 0;
                        }
            
                        cout << result[0];
                        for (int i = 1; i < result.size(); i++) {
                            cout << " " << result[i];
                        }
                        cout << endl;
                    }
                    catch (exception& e) {
                        cout.rdbuf(coutBuf);
                        cerr.rdbuf(cerrBuf);
                        cerr << e.what() << "\\n";
                        return 1;
                    }
                }
                catch (const invalid_argument&) {
                    cerr << "Invalid Input" << endl;
                    return 2;
                }
                catch (exception& e) {
                    cerr << e.what() << "\\n";
                    return 2;
                }
            
                return 0;
            }
            """);
        code.setUserCode("""
            class Solution {
            public:
                vector<int> twoSum(vector<int> nums, int target) {
            
                }
            };
            """);

        code.setSolutionCode("""
            class Solution {
             public:
                 vector<int> twoSum(vector<int> nums, int target) {
                     unordered_map<int, int> seen;
                     for (int i = 0; i < nums.size(); ++i) {
                         int need = target - nums[i];
                         auto it = seen.find(need);
                         if (it != seen.end()) {
                             return { it->second, i };
                         }
                         seen[nums[i]] = i;
                     }
                     return {};
                 }
             };
            """);

        return code;
    }

    private ProblemCode seedPythonCode(Problem problem){
        ProblemCode code = new ProblemCode();
        code.setProblem(problem);
        code.setLanguage(Language.PYTHON);
        code.setDriverCode("""
            import sys
             import os
            
             {{code}}
            
             def main():
                 try:
                     input1 = input().strip()
                     if not input1:
                         raise Exception("nums should contains atleast 2 elements")
            
                     parts = input1.split()
                     if len(parts) < 2:
                         raise Exception("nums should contains atleast 2 elements")
            
                     nums = [int(x) for x in parts]
            
                     input2 = input().strip()
                     if not input2:
                         raise Exception("Target value cannot be empty")
                     target = int(input2)
            
                     try:
                         sol = Solution()
                         original_stdout = sys.stdout
                         original_stderr = sys.stderr
            
                         sys.stdout = open(os.devnull, 'w')
                         sys.stderr = open(os.devnull, 'w')
            
                         result = sol.twoSum(nums, target)
            
                         sys.stdout.close()
                         sys.stderr.close()
                         sys.stdout = original_stdout
                         sys.stderr = original_stderr
            
                         if not result:
                             return
            
                         if len(result) == 2:
                             print(min(result[0], result[1]), max(result[0], result[1]))
                         else:
                             print(" ".join(map(str, result)))
            
                     except Exception as e:
                         sys.stdout = original_stdout
                         sys.stderr = original_stderr
                         import traceback
                         tb = traceback.extract_tb(e.__traceback__)
                         last_frame = tb[-1]
            
                         print(f'  File "{last_frame.filename}", line {last_frame.lineno}', file=sys.stderr)
                         if last_frame.line:
                             print(f'    {last_frame.line}', file=sys.stderr)
                         print(f"{type(e).__name__}: {e}", file=sys.stderr)
                         sys.exit(1)
            
                 except Exception as e:
                     print(e, file=sys.stderr)
                     sys.exit(2)
            
            
             if __name__ == "__main__":
                 main()
            
            """);
        code.setUserCode("""
            class Solution:
                 def twoSum(self, nums: list[int], target: int) -> list[int]:
            
            
            """);

        code.setSolutionCode("""
            class Solution:
                 def twoSum(self, nums: list[int], target: int) -> list[int]:
                     seen = {}
                     for i, num in enumerate(nums):
                         need = target - num
                         if need in seen:
                             return [seen[need], i]
                         seen[num] = i
                     return []
            
            """);

        return code;
    }
}
