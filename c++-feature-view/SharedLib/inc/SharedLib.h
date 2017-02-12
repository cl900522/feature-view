/*
 * SharedLib.h
 *
 *  Created on: 2017年2月8日
 *      Author: chenmx
 */

#ifndef INC_SHAREDLIB_H_
#define INC_SHAREDLIB_H_

class SharedLib {
public:
	SharedLib();
	virtual ~SharedLib();
	void speak(char* words);
};

#endif /* INC_SHAREDLIB_H_ */
